package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewObservationActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbRef;
    FirebaseStorage myStorageRef;
    private ArrayList<String> attributesList;
    private HashMap<String, String> attributesMap;
    private Bitmap bitmap;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private ArrayList<Bitmap> bitmaps;
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_observation);
        checkCameraPermission();

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        myStorageRef = FirebaseStorage.getInstance();
        mStorage = myStorageRef.getReference();
        bitmaps = new ArrayList<>();

        Button capFeaturesButton = findViewById(R.id.buttonCapFeatures);
        ImageButton capFeaturesImageButton = findViewById(R.id.imageButtonCapFeatures);
        Button gillFeaturesButton = findViewById(R.id.buttonGillFeatures);
        ImageButton gillFeaturesImageButton = findViewById(R.id.imageButtonGillFeatures);
        Button stemFeaturesButton = findViewById(R.id.buttonStemFeatures);
        ImageButton stemFeaturesImageButton = findViewById(R.id.imageButtonStemFeatures);
        Button otherFeaturesButton = findViewById(R.id.buttonOtherFeatures);
        ImageButton otherFeaturesImageButton = findViewById(R.id.imageButtonOtherFeatures);

        Button submit = findViewById(R.id.buttonSubmit);

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();

        capFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capFeaturesPage = new Intent(NewObservationActivity.this, CapFeaturesActivity.class);
                startActivity(capFeaturesPage);
            }
        });
        capFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capFeaturesPage = new Intent(NewObservationActivity.this, CapFeaturesActivity.class);
                startActivity(capFeaturesPage);
            }
        });

        gillFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gillFeaturesPage = new Intent(NewObservationActivity.this, GillFeaturesActivity.class);
                startActivity(gillFeaturesPage);
            }
        });
        gillFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gillFeaturesPage = new Intent(NewObservationActivity.this, GillFeaturesActivity.class);
                startActivity(gillFeaturesPage);
            }
        });

        stemFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stemFeaturesPage = new Intent(NewObservationActivity.this, StemFeaturesActivity.class);
                startActivity(stemFeaturesPage);
            }
        });
        stemFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stemFeaturesPage = new Intent(NewObservationActivity.this, StemFeaturesActivity.class);
                startActivity(stemFeaturesPage);
            }
        });

        otherFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherFeaturesPage = new Intent(NewObservationActivity.this, OtherFeaturesActivity.class);
                startActivity(otherFeaturesPage);
            }
        });
        otherFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherFeaturesPage = new Intent(NewObservationActivity.this, OtherFeaturesActivity.class);
                startActivity(otherFeaturesPage);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.valueOf(new Date().getTime());

                if(CapFeaturesActivity.getAttributesList() != null) {
                    for (String feature: CapFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if(GillFeaturesActivity.getAttributesList() != null) {
                    for (String feature: GillFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if(StemFeaturesActivity.getAttributesList() != null) {
                    for (String feature: StemFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if(OtherFeaturesActivity.getAttributesList() != null) {
                    for (String feature: OtherFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }

                HashMap<String, String> capMap = CapFeaturesActivity.getAttributesMap();
                HashMap<String, String> stemMap = StemFeaturesActivity.getAttributesMap();
                HashMap<String, String> gillMap = GillFeaturesActivity.getAttributesMap();
                HashMap<String, String> othersMap = OtherFeaturesActivity.getAttributesMap();


                if(CapFeaturesActivity.getAttributesMap()!= null) {
                    HashMap tempMap = new HashMap(CapFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if(GillFeaturesActivity.getAttributesMap()!= null) {
                    HashMap tempMap = new HashMap(GillFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if(StemFeaturesActivity.getAttributesMap()!= null) {
                    HashMap tempMap = new HashMap(StemFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if(OtherFeaturesActivity.getAttributesMap()!= null) {
                    HashMap tempMap = new HashMap(OtherFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }


                if(attributesList.isEmpty()){
                    Toast.makeText(NewObservationActivity.this,"No data entered",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    for (int i = 0; i < attributesList.size(); i++) {
                        String tag = attributesList.get(i);
                        String type = "Other";
                        if(tag.contains("Cap")){
                            type = "Cap";
                        }
                        else if(tag.contains("Gill")){
                            type = "Gill";
                        }
                        else if(tag.contains("Stalk")){
                            type = "Stem";
                        }
                        dbRef.child("Fungi Attributes:").child(time).child(type).child(tag).
                                setValue(attributesMap.get(tag));
                    }

                    if(CapFeaturesActivity.getBitmapsList() != null) {
                        for (Bitmap photo: CapFeaturesActivity.getBitmapsList()) {
                            bitmaps.add(photo);
                        }
                    }
                    if(GillFeaturesActivity.getBitmapsList() != null) {
                        for (Bitmap photo: GillFeaturesActivity.getBitmapsList()) {
                            bitmaps.add(photo);
                        }
                    }
                    if(StemFeaturesActivity.getBitmapsList() != null) {
                        for (Bitmap photo: StemFeaturesActivity.getBitmapsList()) {
                            bitmaps.add(photo);
                        }
                    }
                    if(OtherFeaturesActivity.getBitmapsList() != null) {
                        for (Bitmap photo: OtherFeaturesActivity.getBitmapsList()) {
                            bitmaps.add(photo);
                        }
                    }

                    uploadPhotos(time);
                    Toast.makeText(NewObservationActivity.this,"Observation submitted",
                            Toast.LENGTH_SHORT).show();
                    attributesList.clear();
                    attributesMap.clear();
                    bitmaps.clear();
                    finish();
                }
            }
        });
    }

    public boolean checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission to access camera")
                        .setMessage("Please allow the app to access you camera.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(NewObservationActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        99);
                            }
                        })
                        .create()
                        .show();
            }
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        99);
            }
            return false;
        }
        else {
            return true;
        }
    }

    private void uploadPhotos(String time){
        if(!bitmaps.isEmpty()) {
            for (Bitmap bmap : bitmaps) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] b = stream.toByteArray();
                String newTime = String.valueOf(new Date().getTime());
                mStorage.child("Images").child(time).child(newTime).putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewObservationActivity.this, "Photo upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Toast.makeText(NewObservationActivity.this, "Photo(s) uploaded", Toast.LENGTH_SHORT).show();
        }
    }
}