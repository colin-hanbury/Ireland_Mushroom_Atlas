package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClassifyActivity extends AppCompatActivity {

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "MobilenetV1/Predictions/Softmax";

    private static final String MODEL_FILE = "file:///android_asset/graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/labels.txt";
    private static final int CAMERA_REQUEST_CODE = 1;

    private Classifier classifier;
    private Button classifyImage;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

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
                startActivityForResult(camera, CAMERA_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            image = (Bitmap) data.getExtras().get("data");
        }
        classifier.recognizeImage(image);
    }
}
