package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.Manifest;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class NewObservationActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseStorage myStorageRef;
    private ArrayList<String> attributesList;
    private HashMap<String, String> attributesMap;
    private StorageReference mStorage;
    private ArrayList<Bitmap> bitmaps;
    private LatLng mushroomLocation;
    private String capURL = "";
    private String gillURL = "gillPhoto";
    private String stemURL = "stemPhoto";
    private String veilRingURL = "veilRingPhoto";
    private String otherURL = "otherPhoto";

    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_observation);
        mProgressBar = findViewById(R.id.progressBar);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        myStorageRef = FirebaseStorage.getInstance();
        mStorage = myStorageRef.getReference();

        bitmaps = new ArrayList<>();
        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();


        getLocation();

        checkCameraPermission();

        Button takePhotosButton = findViewById(R.id.buttonTakePhotos);
        ImageButton takePhotosImageButton = findViewById(R.id.imageButtonTakePhotos);

        Button capFeaturesButton = findViewById(R.id.buttonCapFeatures);
        ImageButton capFeaturesImageButton = findViewById(R.id.imageButtonCapFeatures);
        Button gillFeaturesButton = findViewById(R.id.buttonGillFeatures);
        ImageButton gillFeaturesImageButton = findViewById(R.id.imageButtonGillFeatures);
        Button stemFeaturesButton = findViewById(R.id.buttonStemFeatures);
        ImageButton stemFeaturesImageButton = findViewById(R.id.imageButtonStemFeatures);
        Button veilRingFeaturesButton = findViewById(R.id.buttonVeilRingFeatures);
        ImageButton veilRingFeaturesImageButton = findViewById(R.id.imageButtonVeilRingFeatures);
        Button otherFeaturesButton = findViewById(R.id.buttonOtherFeatures);
        ImageButton otherFeaturesImageButton = findViewById(R.id.imageButtonOtherFeatures);

        Button submit = findViewById(R.id.buttonSubmit);



        takePhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotosPage = new Intent(NewObservationActivity.this,
                        TakePhotosActivity.class);
                startActivity(takePhotosPage);
            }
        });
        takePhotosImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotosPage = new Intent(NewObservationActivity.this,
                        TakePhotosActivity.class);
                startActivity(takePhotosPage);
            }
        });

        capFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capFeaturesPage = new Intent(NewObservationActivity.this,
                        CapFeaturesActivity.class);
                startActivity(capFeaturesPage);
            }
        });
        capFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capFeaturesPage = new Intent(NewObservationActivity.this,
                        CapFeaturesActivity.class);
                startActivity(capFeaturesPage);
            }
        });

        gillFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gillFeaturesPage = new Intent(NewObservationActivity.this,
                        GillFeaturesActivity.class);
                startActivity(gillFeaturesPage);
            }
        });
        gillFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gillFeaturesPage = new Intent(NewObservationActivity.this,
                        GillFeaturesActivity.class);
                startActivity(gillFeaturesPage);
            }
        });

        stemFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stemFeaturesPage = new Intent(NewObservationActivity.this,
                        StemFeaturesActivity.class);
                startActivity(stemFeaturesPage);
            }
        });
        stemFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stemFeaturesPage = new Intent(NewObservationActivity.this,
                        StemFeaturesActivity.class);
                startActivity(stemFeaturesPage);
            }
        });

        veilRingFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherFeaturesPage = new Intent(NewObservationActivity.this,
                        VeilRingActivity.class);
                startActivity(otherFeaturesPage);
            }
        });
        veilRingFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherFeaturesPage = new Intent(NewObservationActivity.this,
                        VeilRingActivity.class);
                startActivity(otherFeaturesPage);
            }
        });

        otherFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherFeaturesPage = new Intent(NewObservationActivity.this,
                        OtherFeaturesActivity.class);
                startActivity(otherFeaturesPage);
            }
        });
        otherFeaturesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherFeaturesPage = new Intent(NewObservationActivity.this,
                        OtherFeaturesActivity.class);
                startActivity(otherFeaturesPage);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.valueOf(new Date().getTime());

                if (CapFeaturesActivity.getAttributesList() != null) {
                    for (String feature : CapFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if (GillFeaturesActivity.getAttributesList() != null) {
                    for (String feature : GillFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if (StemFeaturesActivity.getAttributesList() != null) {
                    for (String feature : StemFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if (VeilRingActivity.getAttributesList() != null) {
                    for (String feature : VeilRingActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                if (OtherFeaturesActivity.getAttributesList() != null) {
                    for (String feature : OtherFeaturesActivity.getAttributesList()) {
                        attributesList.add(feature);
                    }
                }
                ///////////////////////////////////////////////////////////////////////

                if (CapFeaturesActivity.getAttributesMap() != null) {
                    HashMap tempMap = new HashMap(CapFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if (GillFeaturesActivity.getAttributesMap() != null) {
                    HashMap tempMap = new HashMap(GillFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if (StemFeaturesActivity.getAttributesMap() != null) {
                    HashMap tempMap = new HashMap(StemFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if (VeilRingActivity.getAttributesMap() != null) {
                    HashMap tempMap = new HashMap(VeilRingActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }
                if (OtherFeaturesActivity.getAttributesMap() != null) {
                    HashMap tempMap = new HashMap(OtherFeaturesActivity.getAttributesMap());
                    tempMap.keySet().removeAll(attributesMap.keySet());
                    attributesMap.putAll(tempMap);
                }

                if (attributesList.isEmpty() && bitmaps.isEmpty()) {
                    Toast.makeText(NewObservationActivity.this, "No data entered",
                            Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < attributesList.size(); i++) {
                        String tag = attributesList.get(i);
                        String type = "";
                        if (tag != null) {
                            if (tag.contains("Cap")) {
                                type = "cap";
                            } else if (tag.contains("Gill")) {
                                type = "gill";
                            } else if (tag.contains("Stem")) {
                                type = "stem";
                            } else if (tag.contains("Veil") || tag.contains("Ring")) {
                                type = "veil_ring";
                            } else {
                                type = "other";
                            }
                        }

                        dbRef.child("mushroom_attributes:").child(time).child(type).child(tag).
                                setValue(attributesMap.get(tag));
                    }
                    getLocation();
                    dbRef.child("mushroom_locations").child(time).setValue(mushroomLocation);
                    uploadPhotos(time);
                    if (mushroomLocation == null){
                        Toast.makeText(NewObservationActivity.this,"Location Not Submitted with Observation",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(NewObservationActivity.this,"Location Submitted with Observation",
                                Toast.LENGTH_SHORT).show();
                    }

                    attributesList.clear();
                    attributesMap.clear();
                    bitmaps.clear();
                    finish();
                }
            }
        });
    }

    private void uploadPhotos(final String time){

        if (!TakePhotosActivity.getBitmapsLists().isEmpty()) {
            if (!TakePhotosActivity.getBitmapsLists().get("cap").isEmpty()) {
                for (Bitmap photo : TakePhotosActivity.getBitmapsLists().get("cap")) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] b = stream.toByteArray();
                    final String newTime = String.valueOf(new Date().getTime());
                    mStorage.child("mushroom_photos").child(time).child("cap").child(newTime)
                            .putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() { }
                            }, 500);
                            mProgressBar.setProgress(0);
                            capURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            dbRef.child("mushroom_photos").child(time).child("cap").child(newTime)
                                    .setValue(capURL);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewObservationActivity.this,
                                    "Photo upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
            if (!TakePhotosActivity.getBitmapsLists().get("gill").isEmpty()){
                for (Bitmap photo : TakePhotosActivity.getBitmapsLists().get("gill")) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] b = stream.toByteArray();
                    String newTime = String.valueOf(new Date().getTime());
                    mStorage.child("mushroom_photos").child(time).child("gill").child(newTime)
                            .putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() { }
                            }, 500);
                            mProgressBar.setProgress(0);
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            String imageUrl = urlTask.toString();
                            gillURL = imageUrl;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewObservationActivity.this,
                                    "Photo upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
                    dbRef.child("mushroom_photos").child(time).child("gill").child(newTime)
                            .setValue(gillURL);
                }
            }
            if (!TakePhotosActivity.getBitmapsLists().get("stem").isEmpty()) {
                for (Bitmap photo : TakePhotosActivity.getBitmapsLists().get("stem")) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] b = stream.toByteArray();
                    String newTime = String.valueOf(new Date().getTime());
                    mStorage.child("mushroom_photos").child(time).child("stem").child(newTime)
                            .putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            String imageUrl = urlTask.toString();
                            stemURL = imageUrl;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewObservationActivity.this,
                                    "Photo upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dbRef.child("mushroom_photos").child(time).child("stem").child(newTime)
                            .setValue(stemURL);
                }
            }
            if (!TakePhotosActivity.getBitmapsLists().get("veilRing").isEmpty()) {
                for (Bitmap photo : TakePhotosActivity.getBitmapsLists().get("veilRing")) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] b = stream.toByteArray();
                    String newTime = String.valueOf(new Date().getTime());
                    mStorage.child("mushroom_photos").child(time).child("veilRing").child(newTime)
                            .putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            String imageUrl = urlTask.toString();
                            veilRingURL = imageUrl;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewObservationActivity.this,
                                    "Photo upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dbRef.child("mushroom_photos").child(time).child("veilRing").child(newTime)
                            .setValue(veilRingURL);
                }
            }
            if (!TakePhotosActivity.getBitmapsLists().get("other").isEmpty()) {
                for (Bitmap photo : TakePhotosActivity.getBitmapsLists().get("other")) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] b = stream.toByteArray();
                    String newTime = String.valueOf(new Date().getTime());
                    mStorage.child("mushroom_photos").child(time).child("other").child(newTime)
                            .putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            String imageUrl = urlTask.toString();
                            otherURL = imageUrl;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewObservationActivity.this,
                                    "Photo upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dbRef.child("mushroom_photos").child(time).child("other").child(newTime)
                            .setValue(otherURL);
                }
            }
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission to access GPS denied", Toast.LENGTH_SHORT).show();

            //LocationDialog.dismiss();
            return;
        }

        SingleShotLocationProvider.requestSingleUpdate(this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        mushroomLocation = new LatLng(location.latitude, location.longitude);

                        //addMarker(latLng);
                        //LocationDialog.dismiss();
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
}