package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ClassifyActivity extends AppCompatActivity {

    private static final int INPUT_SIZE = 229;
    private static final int IMAGE_MEAN = 100;
    //private static final int INPUT_SIZE = 1024;
    //private static final int IMAGE_MEAN = 512;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/retrained_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/retrained_labels.txt";
    private static final int CAMERA_REQUEST_CODE = 1;
    private Classifier classifier;
    private Button classifyImage;
    private ImageView classifiedMushroomImage;
    private TextView classifiedMushroomText;
    private String filePath;
    private Context context;
    private Uri uri;
    private List<Classifier.Recognition> results;
    private String r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        context = ClassifyActivity.this;
        r = "";
        classifiedMushroomImage = findViewById(R.id.classifyMushroomImage);
        classifiedMushroomText = findViewById(R.id.classifyMushroomName);
        classifier =
                TensorFlowImageClassifier.create(
                        getAssets(),
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME);

        classifyImage = findViewById(R.id.classifyImageButton);
        classifyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /*
                try {
                    uri = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    ToastMessage(anfe.getMessage());
                }
                catch (IOException e){
                    ToastMessage(e.getMessage());
                }
                camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //*/
                startActivityForResult(camera, CAMERA_REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_REQUEST_CODE){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            /*
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,1,stream);
            byte[] byteArray = stream.toByteArray();
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            */
            results = classifier.recognizeImage(bitmap);
            classifiedMushroomImage.setImageBitmap(bitmap);
            r = "" ;
            ///*
            for(Classifier.Recognition result : results){
                r += result.getTitle() + ": " + result.getConfidence() + "\n";
            }
            //*/
            classifiedMushroomText.setText(r);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = String.valueOf(new Date().getTime());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        filePath = image.getAbsolutePath();
        return image;
    }

    private void ToastMessage(String message){
        Toast.makeText(ClassifyActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
