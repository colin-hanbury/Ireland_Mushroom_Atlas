package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TakePhotosActivity extends AppCompatActivity {

    private boolean genericPhoto;
    private Button addPhotoGeneric;
    private static ArrayList<String> pathsGenericFiles;

    private boolean capPhoto;
    private Button addPhotoCap;
    private static ArrayList<String> pathsCapFiles;

    private boolean gillPhoto;
    private Button addPhotoGill;
    private static ArrayList<String> pathsGillFiles;

    private boolean stemPhoto;
    private Button addPhotoStem;
    private static ArrayList<String> pathsStemFiles;

    private boolean veilRingPhoto;
    private Button addPhotoVeilRing;
    private static ArrayList<String> pathsVeilRingFiles;

    private boolean otherPhoto;
    private Button addPhotoOther;
    private static ArrayList<String> pathsOtherFiles;

    private Uri uriGeneric;
    private Uri uriCap;
    private Uri uriGill;
    private Uri uriStem;
    private Uri uriVeilRing;
    private Uri uriOther;

    private static HashMap<String, ArrayList<String>> paths;
    private Button saveAndReturn;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Context context;
    private String mCurrentPhotoPath;
    private String ioException;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photos);

        genericPhoto = false;
        capPhoto = false;
        gillPhoto = false;
        stemPhoto = false;
        veilRingPhoto = false;
        otherPhoto = false;
        pathsGenericFiles = new ArrayList<>();
        pathsCapFiles = new ArrayList<>();
        pathsGillFiles = new ArrayList<>();
        pathsStemFiles = new ArrayList<>();
        pathsVeilRingFiles = new ArrayList<>();
        pathsOtherFiles = new ArrayList<>();
        paths = new HashMap<>();
        context = TakePhotosActivity.this;

        addPhotoGeneric = findViewById(R.id.buttonAddGenericPhotos);
        addPhotoGeneric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genericPhoto = true;
                // use standard intent to capture an image
                Intent cameraPhotoGeneric = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    uriGeneric = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                    genericPhoto = false;
                }
                catch (IOException e){
                    Toast.makeText(TakePhotosActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG);
                    ioException = e.getMessage();
                    genericPhoto = false;
                }
                cameraPhotoGeneric.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraPhotoGeneric.putExtra(MediaStore.EXTRA_OUTPUT, uriGeneric);
                startActivityForResult(cameraPhotoGeneric, CAMERA_REQUEST_CODE);
            }
        });

        addPhotoCap = findViewById(R.id.buttonAddCapPhotos);
        addPhotoCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capPhoto = true;
                // use standard intent to capture an image
                Intent cameraPhotoCap = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    uriCap = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                    capPhoto = false;
                }
                catch (IOException e){
                    Toast.makeText(TakePhotosActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG);
                    ioException = e.getMessage();
                    capPhoto = false;
                }
                cameraPhotoCap.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraPhotoCap.putExtra(MediaStore.EXTRA_OUTPUT, uriCap);
                startActivityForResult(cameraPhotoCap, CAMERA_REQUEST_CODE);
            }
        });

        addPhotoGill = findViewById(R.id.buttonAddGillPhotos);
        addPhotoGill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gillPhoto = true;
                Intent cameraPhotoGill = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    uriGill = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                    gillPhoto = false;
                }
                catch (IOException e){
                    Toast.makeText(TakePhotosActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG);
                    ioException = e.getMessage();
                    gillPhoto = false;
                }
                cameraPhotoGill.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraPhotoGill.putExtra(MediaStore.EXTRA_OUTPUT, uriGill);
                startActivityForResult(cameraPhotoGill, CAMERA_REQUEST_CODE);
            }
        });

        addPhotoStem = findViewById(R.id.buttonAddStemPhotos);
        addPhotoStem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stemPhoto = true;
                Intent cameraPhotoStem = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    uriStem = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                    stemPhoto = false;
                }
                catch (IOException e){
                    Toast.makeText(TakePhotosActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG);
                    ioException = e.getMessage();
                    stemPhoto = false;
                }
                cameraPhotoStem.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraPhotoStem.putExtra(MediaStore.EXTRA_OUTPUT, uriStem);
                startActivityForResult(cameraPhotoStem, CAMERA_REQUEST_CODE);
            }
        });

        addPhotoVeilRing = findViewById(R.id.buttonAddVeilRingPhotos);
        addPhotoVeilRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veilRingPhoto = true;
                Intent cameraPhotoVeilRing = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    uriVeilRing = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                    veilRingPhoto = false;
                }
                catch (IOException e){
                    Toast.makeText(TakePhotosActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG);
                    ioException = e.getMessage();
                    veilRingPhoto = false;
                }
                cameraPhotoVeilRing.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraPhotoVeilRing.putExtra(MediaStore.EXTRA_OUTPUT, uriVeilRing);
                startActivityForResult(cameraPhotoVeilRing, CAMERA_REQUEST_CODE);
            }
        });

        addPhotoOther = findViewById(R.id.buttonAddOtherPhotos);
        addPhotoOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherPhoto = true;
                Intent cameraPhotoOther = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    uriOther = FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                }
                catch (ActivityNotFoundException anfe) {
                    Toast.makeText(TakePhotosActivity.this,"Exception",
                            Toast.LENGTH_LONG);
                    otherPhoto = false;
                }
                catch (IOException e){
                    Toast.makeText(TakePhotosActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG);
                    ioException = e.getMessage();
                    otherPhoto = false;
                }
                cameraPhotoOther.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraPhotoOther.putExtra(MediaStore.EXTRA_OUTPUT, uriOther);
                startActivityForResult(cameraPhotoOther, CAMERA_REQUEST_CODE);
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
        if(requestCode == CAMERA_REQUEST_CODE){
            if(genericPhoto == true){
                pathsGenericFiles.add(mCurrentPhotoPath);
                genericPhoto = false;
            }
            if(capPhoto == true){
                pathsCapFiles.add(mCurrentPhotoPath);
                capPhoto = false;
            }
            else if(gillPhoto == true){
                pathsGillFiles.add(mCurrentPhotoPath);
                gillPhoto = false;
            }
            else if(stemPhoto == true){
                pathsStemFiles.add(mCurrentPhotoPath);
                stemPhoto = false;
            }
            else if(veilRingPhoto == true){
                pathsVeilRingFiles.add(mCurrentPhotoPath);
                veilRingPhoto = false;
            }
            else if(otherPhoto == true){
                pathsOtherFiles.add(mCurrentPhotoPath);
                otherPhoto = false;
            }
            else {
                Toast.makeText(TakePhotosActivity.this, ioException,
                        Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(TakePhotosActivity.this, "Photo ready to be uploaded",
                    Toast.LENGTH_LONG).show();
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static HashMap<String, ArrayList<String>> getPaths(){
        paths.put("generic", pathsGenericFiles);
        paths.put("cap", pathsCapFiles);
        paths.put("gill", pathsGillFiles);
        paths.put("stem", pathsStemFiles);
        paths.put("veilRing", pathsVeilRingFiles);
        paths.put("other", pathsOtherFiles);
        return paths;
    }
}
