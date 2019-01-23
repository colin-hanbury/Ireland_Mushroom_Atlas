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
import java.util.HashMap;

public class TakePhotosActivity extends AppCompatActivity {

    private boolean capPhoto;
    private Button addPhotoCap;
    private static ArrayList<Bitmap> bitmapsCap;

    private boolean gillPhoto;
    private Button addPhotoGill;
    private static ArrayList<Bitmap> bitmapsGill;

    private boolean stemPhoto;
    private Button addPhotoStem;
    private static ArrayList<Bitmap> bitmapsStem;

    private boolean veilRingPhoto;
    private Button addPhotoVeilRing;
    private static ArrayList<Bitmap> bitmapsVeilRing;

    private boolean otherPhoto;
    private Button addPhotoOther;
    private static ArrayList<Bitmap> bitmapsOther;

    private Button saveAndReturn;
    private Bitmap bitmap;
    private static HashMap<String, ArrayList<Bitmap>> bitmaps;

    private static final int CAMERA_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photos);
        capPhoto = false;
        gillPhoto = false;
        stemPhoto = false;
        veilRingPhoto = false;
        otherPhoto = false;
        bitmapsCap = new ArrayList<>();
        bitmapsGill = new ArrayList<>();
        bitmapsStem = new ArrayList<>();
        bitmapsVeilRing = new ArrayList<>();
        bitmapsOther = new ArrayList<>();
        bitmaps = new HashMap<>();

        addPhotoCap = findViewById(R.id.buttonAddCapPhotos);
        addPhotoCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    capPhoto = true;
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
                    gillPhoto = true;
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
                    stemPhoto = true;
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
                    veilRingPhoto = true;
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
                    otherPhoto = true;
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
            if(capPhoto == true){
                bitmapsCap.add(bitmap);
                capPhoto = false;
            }
            else if(gillPhoto == true){
                bitmapsGill.add(bitmap);
                gillPhoto = false;
            }
            else if(stemPhoto == true){
                bitmapsStem.add(bitmap);
                stemPhoto = false;
            }
            else if(veilRingPhoto == true){
                bitmapsVeilRing.add(bitmap);
                veilRingPhoto = false;
            }
            else if(otherPhoto == true){
                bitmapsOther.add(bitmap);
                otherPhoto = false;
            }
            else {
                return;
            }
            Toast.makeText(TakePhotosActivity.this, "Photo ready to be uploaded",
                    Toast.LENGTH_LONG).show();
        }

    }

    public static HashMap<String, ArrayList<Bitmap>> getBitmapsLists(){
        bitmaps.put("cap", bitmapsCap);
        bitmaps.put("gill", bitmapsGill);
        bitmaps.put("stem", bitmapsStem);
        bitmaps.put("veilRing", bitmapsVeilRing);
        bitmaps.put("other", bitmapsOther);
        return bitmaps;
    }
}
