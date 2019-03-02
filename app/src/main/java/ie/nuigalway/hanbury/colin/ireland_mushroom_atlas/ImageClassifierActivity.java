package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ImageClassifierActivity extends AppCompatActivity {
    private ImagePreprocessor mImagePreprocessor;
    private Classifier mTensorFlowClassifier;
    private HandlerThread backgroundThread;
    private Handler backgroundHandler;
    private Button captureButton;
    private TextView resultText;
    private static final int MAX_IMAGES = 1;
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private boolean initialized;
    private Size imageDimensions;
    private TextureView textureView;
    private CaptureRequest.Builder captureRequestBuilder;
    private ImageReader mImageReader;
    private Context context;
    // Matches the images used to train the TensorFlow model
    private static final Size MODEL_IMAGE_SIZE = new Size(224, 224);
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final String MODEL_FILE = "file:///android_asset/retrained_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/retrained_labels.txt";
    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";
    private static final String TAG = "ImageClassifierActivity";
    //Callback handling device state changes
    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.d(TAG, "Opened camera.");
            cameraDevice = camera;
            createCameraPreview();
            backgroundHandler.post(mInitializeOnBackground);
        }
        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.d(TAG, "Camera disconnected, closing.");
            cameraDevice.close();
        }
        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            Log.d(TAG, "Camera device error, closing.");
            cameraDevice.close();
        }
        @Override
        public void onClosed(@NonNull CameraDevice cameraDevice) {
            Log.d(TAG, "Closed camera, releasing");
        }
    };
    private boolean previewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ImageClassifierActivity.this;
        setContentView(R.layout.activity_camera);
        textureView = findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(textureListener);
        previewMode = true;
        captureButton = findViewById(R.id.buttonCapture);
        resultText = findViewById(R.id.resultText);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previewMode) {
                    takePicture();
                }
                else {
                    createCameraPreview();
                    captureButton.setText("Capture");
                    resultText.setText("");
                }
            }
        });

    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            initializeCamera();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(textureView.isAvailable()){
            initializeCamera();
        }
        else {
            textureView.setSurfaceTextureListener(textureListener);
        }

        startBackgroundThread();
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    private void stopBackgroundThread() {
        backgroundThread.quitSafely();
        try{
            backgroundThread.join();
            backgroundThread= null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        backgroundThread = new HandlerThread("Camera Background");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

    }

    private Runnable mInitializeOnBackground = new Runnable() {
        @Override
        public void run() {
            Size cameraCaptureSize = getImageDimensions();
            mImagePreprocessor =
                    new ImagePreprocessor(cameraCaptureSize.getWidth(), cameraCaptureSize
                            .getHeight(),
                            MODEL_IMAGE_SIZE.getWidth(), MODEL_IMAGE_SIZE.getHeight());

            mTensorFlowClassifier = TensorFlowImageClassifier.create(getAssets(),
                    MODEL_FILE,
                    LABEL_FILE,
                    INPUT_SIZE,
                    IMAGE_MEAN,
                    IMAGE_STD,
                    INPUT_NAME,
                    OUTPUT_NAME);
        }
    };

    private void initializeCamera() {
        if (initialized) {
            throw new IllegalStateException(
                    "CameraHandler is already initialized or is initializing");
        }
        initialized = true;

        // Discover the camera instance
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try{
            String camId = getCameraId(context);
            // Initialize the image processor with the largest available size.
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(camId);
            StreamConfigurationMap map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            Size bestSize = getBestCameraSize(map.getOutputSizes(ImageFormat.JPEG),
                    MODEL_IMAGE_SIZE);
            if (bestSize == null) {
                throw new RuntimeException(
                        "We could not find a camera resolution that is larger than "
                        + MODEL_IMAGE_SIZE.getWidth() + "x" + MODEL_IMAGE_SIZE.getHeight());
            }

            imageDimensions = bestSize;

            // Open the camera resource
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(camId, stateCallback, null);
        } catch (CameraAccessException cae) {
            cae.printStackTrace();
            Log.e(TAG, "Camera access exception", cae);
        }
    }

    private void createCameraPreview() {
        try{
            SurfaceTexture texture = textureView.getSurfaceTexture();
            texture.setDefaultBufferSize(imageDimensions.getWidth(),imageDimensions.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession
                    .StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if(cameraDevice == null) {
                        return;
                    }
                    Log.d(TAG, "Create preview success");
                    captureSession = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Log.d(TAG, "Create preview failed");
                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        previewMode = true;
    }

    private void updatePreview() {
        if(cameraDevice == null) {
            Log.d(TAG, "Camera device null @ updatePreview()");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        try{
            captureSession.setRepeatingRequest(captureRequestBuilder.build(),null,
                    backgroundHandler);
            Log.d(TAG, "Update preview success");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    //Begin a still image capture
    private void takePicture() {
        if (cameraDevice == null) {
            Log.w(TAG, "Cannot capture image. Camera not initialized.");
            return;
        }
        // Create a CameraCaptureSession for capturing still images.
        try {
            ImageReader.OnImageAvailableListener imageAvailableListener = new ImageReader
                    .OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    final Bitmap bitmap;
                    try (Image image = reader.acquireNextImage()) {
                        bitmap = mImagePreprocessor.preprocessImage(image);
                    }

                    final List<Classifier.Recognition> results = mTensorFlowClassifier
                            .recognizeImage(bitmap);
                    Log.d(TAG, "Got the following results from Tensorflow: " + results);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (results == null || results.isEmpty()) {
                                resultText.setText("I don't understand what I see");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                Iterator<Classifier.Recognition> it = results.iterator();
                                while (it.hasNext()) {
                                    Classifier.Recognition r = it.next();
                                    sb.append(r.getTitle());
                                    sb.append(": ");
                                    sb.append(r.getConfidence());
                                    sb.append("\n");
                                }
                                resultText.setText(sb.toString());
                                captureButton.setText("New Capture");
                            }
                        }
                    });
                }
            };
            mImageReader = ImageReader.newInstance(imageDimensions.getWidth(), imageDimensions
                            .getHeight(),
                    ImageFormat.JPEG, MAX_IMAGES);
            Log.d(TAG, "Will capture photos that are " + imageDimensions.getWidth() + " x " +
                    imageDimensions.getHeight());
            mImageReader.setOnImageAvailableListener(imageAvailableListener, backgroundHandler);

            final CaptureRequest.Builder captureBuilder =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(mImageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
            Log.d(TAG, "Capture request created.");

            final CameraCaptureSession.CaptureCallback captureListener =
                    new CameraCaptureSession.CaptureCallback() {
                        @Override
                        public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                                        @NonNull CaptureRequest request,
                                                        @NonNull CaptureResult partialResult) {
                            Log.d(TAG, "Partial result");
                        }
                        @Override
                        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                                       @NonNull CaptureRequest request,
                                                       @NonNull TotalCaptureResult result) {
                            session.close();
                            Log.d(TAG, "CaptureSession closed");
                        }
                    };

            cameraDevice.createCaptureSession(
                    Collections.singletonList(mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    // The camera is already closed
                    if (cameraDevice == null) {
                        return;
                    }
                    // When the session is ready, we start capture.
                    try {
                        cameraCaptureSession.capture(captureBuilder.build(), captureListener,
                                backgroundHandler);
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Log.w(TAG, "Failed to configure camera");
                }
            }, backgroundHandler);

        } catch (CameraAccessException cae) {
            Log.e(TAG, "Cannot create camera capture session", cae);
        }
        previewMode = false;
    }

    public Size getImageDimensions() {
        return imageDimensions;
    }

    public static String getCameraId(Context context) {
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String[] camIds = null;
        try {
            camIds = manager.getCameraIdList();
        } catch (CameraAccessException e) {
            Log.w(TAG, "Cannot get the list of available cameras", e);
        }
        if (camIds == null || camIds.length < 1) {
            Log.d(TAG, "No cameras found");
            return null;
        }
        return camIds[0];
    }

    /**
     * Helpful debugging method:  Dump all supported camera formats to log.  You don't need to run
     * this for normal operation, but it's very helpful when porting this code to different
     * hardware.
     */
    public static void dumpFormatInfo(Context context) {
        // Discover the camera instance
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String camId = getCameraId(context);
        if (camId == null) {
            return;
        }
        Log.d(TAG, "Using camera id " + camId);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(camId);
            StreamConfigurationMap configs = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            for (int format : configs.getOutputFormats()) {
                Log.d(TAG, "Getting sizes for format: " + format);
                for (Size s : configs.getOutputSizes(format)) {
                    Log.d(TAG, "\t" + s.toString());
                }
            }
            int[] effects = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);
            for (int effect : effects) {
                Log.d(TAG, "Effect available: " + effect);
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Camera access exception getting characteristics.");
        }
    }

    static Size getBestCameraSize(Size[] availableCameraResolutions, Size minSize) {
        // This should select the closest size that is not too small
        Arrays.sort(availableCameraResolutions, new CompareSizesByArea()); // Sort by smallest first
        for (Size resolution : availableCameraResolutions) {
            if (resolution.getWidth() >= minSize.getWidth() &&
                    resolution.getHeight() >= minSize.getHeight()) {
                return resolution;
            }
        }
        return null;
    }

    /**
     * Compares two {@code Size}s based on their areas ascending.
     */
    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }
}