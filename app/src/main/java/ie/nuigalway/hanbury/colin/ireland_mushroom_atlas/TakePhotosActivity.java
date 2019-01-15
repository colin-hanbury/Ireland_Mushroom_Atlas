package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class TakePhotosActivity extends AppCompatActivity {

    private Button addPhotoCap;
    //private Bitmap bitmapCap;
    //private static ArrayList<Bitmap> bitmapsCap;
    private Button addPhotoGill;
    //private Bitmap bitmapGill;
    //private static ArrayList<Bitmap> bitmapsGill;
    private Button addPhotoStem;
    //private Bitmap bitmapStem;
    //private static ArrayList<Bitmap> bitmapsStem;
    private Button addPhotoVeilRing;
    //private Bitmap bitmapVeilRing;
    //private static ArrayList<Bitmap> bitmapsVeilRing;
    private Button addPhotoOther;
    //private Bitmap bitmapOther;
    //private static ArrayList<Bitmap> bitmapsOther;

    private Button saveAndReturn;
    private Bitmap bitmap;
    private static ArrayList<Bitmap> bitmaps;

    private static final int CAMERA_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photos);
        bitmaps = new ArrayList<>();

        addPhotoCap = findViewById(R.id.buttonAddCapPhotos);
        addPhotoCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // use standard intent to capture an image
                    Intent cameraPhotoCap = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraPhotoCap, CAMERA_REQUEST_CODE);
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                }
            }
        });

        addPhotoGill = findViewById(R.id.buttonAddGillPhotos);
        addPhotoGill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // use standard intent to capture an image
                    Intent cameraPhotoGill = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraPhotoGill, CAMERA_REQUEST_CODE);
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                        Toast.LENGTH_LONG);
                }
            }
        });

        addPhotoStem = findViewById(R.id.buttonAddStemPhotos);
        addPhotoStem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // use standard intent to capture an image
                    Intent cameraPhotoStem = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraPhotoStem, CAMERA_REQUEST_CODE);
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                }
            }
        });

        addPhotoVeilRing = findViewById(R.id.buttonAddVeilRingPhotos);
        addPhotoVeilRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // use standard intent to capture an image
                    Intent cameraPhotoVeilRing = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraPhotoVeilRing, CAMERA_REQUEST_CODE);
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                        Toast.LENGTH_LONG);
                }
            }
        });

        addPhotoOther = findViewById(R.id.buttonAddOtherPhotos);

        addPhotoOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // use standard intent to capture an image
                    Intent cameraPhotoOther = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraPhotoOther, CAMERA_REQUEST_CODE);
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                }
            }
        });

        saveAndReturn = findViewById(R.id.buttonSaveAndReturnTakePhotos);
        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            bitmaps.add(bitmap);
            Toast.makeText(TakePhotosActivity.this, "Photo ready to be uploaded",
                    Toast.LENGTH_LONG).show();
        }

    }

    public static ArrayList<Bitmap> getBitmapsList(){
        return bitmaps;
    }
}
