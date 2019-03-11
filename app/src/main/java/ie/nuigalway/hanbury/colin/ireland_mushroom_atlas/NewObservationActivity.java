package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseUser;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// c.hanbury1@nuigalway.ie
// ilikemushrooms123

public class NewObservationActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseStorage myStorageRef;
    private ArrayList<String> attributesList;
    private HashMap<String, String> attributesMap;
    private StorageReference mStorage;
    private LatLng mushroomLocation;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_observation);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        myStorageRef = FirebaseStorage.getInstance();
        mStorage = myStorageRef.getReference();

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();


        getLocation();



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

                if (attributesList.isEmpty()) {
                    toast("No data entered");
                }
                else {

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
                }
                getLocation();
                dbRef.child("mushroom_locations").child(time).setValue(mushroomLocation);
                FirebaseUser user = auth.getCurrentUser();
                String username = user.getEmail().split("@", 2)[0];
                username = username.replace(".", "");
                dbRef.child("user").child(time).setValue(username);
                uploadPhotos(time);
                if (mushroomLocation != null){
                    toast("Observation Submitted");
                }
                attributesList.clear();
                attributesMap.clear();
                finish();
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(NewObservationActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void uploadPhotos(final String time){
        if (!TakePhotosActivity.getPaths().isEmpty()) {
            if (!TakePhotosActivity.getPaths().get("generic").isEmpty()) {
                for (String path : TakePhotosActivity.getPaths().get("generic")) {
                    String pathName = path;
                    File photo = new File(path);
                    String genericTime = String.valueOf(new Date().getTime());
                    final StorageReference genericStorageRef = mStorage.child("mushroom_photos").child(time).child("generic").child(genericTime);
                    Task uploadTask = genericStorageRef.putFile(Uri.fromFile(photo));
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return genericStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String currTime = String.valueOf(new Date().getTime());
                                String genericURL = task.getResult().toString();
                                dbRef.child("mushroom_photos").child(time).child("generic").child(currTime)
                                        .setValue(genericURL);
                            }
                        }
                    });
                }
            }
        }
        if (!TakePhotosActivity.getPaths().isEmpty()) {
            if (!TakePhotosActivity.getPaths().get("cap").isEmpty()) {
                for (String path : TakePhotosActivity.getPaths().get("cap")) {
                    String pathName = path;
                    File photo = new File(path);
                    String capTime = String.valueOf(new Date().getTime());
                    final StorageReference capStorageRef = mStorage.child("mushroom_photos").child(time).child("cap").child(capTime);
                    Task uploadTask = capStorageRef.putFile(Uri.fromFile(photo));
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return capStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String currTime = String.valueOf(new Date().getTime());
                                String capURL = task.getResult().toString();
                                dbRef.child("mushroom_photos").child(time).child("cap").child(currTime)
                                        .setValue(capURL);
                            }
                        }
                    });
                }
            }
            if (!TakePhotosActivity.getPaths().get("gill").isEmpty()){
                for (String path : TakePhotosActivity.getPaths().get("gill")) {
                    File photo = new File(path);
                    String gillTime = String.valueOf(new Date().getTime());
                    final StorageReference gillStorageRef = mStorage.child("mushroom_photos").child(time).child("gill").child(gillTime);
                    Task uploadTask = gillStorageRef.putFile(Uri.fromFile(photo));
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return gillStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String currTime = String.valueOf(new Date().getTime());
                                String gillURL = task.getResult().toString();
                                dbRef.child("mushroom_photos").child(time).child("gill").child(currTime)
                                        .setValue(gillURL);
                            }
                        }
                    });
                }
            }
            if (!TakePhotosActivity.getPaths().get("stem").isEmpty()) {
                for (String path : TakePhotosActivity.getPaths().get("stem")) {
                    File photo = new File(path);
                    String stemTime = String.valueOf(new Date().getTime());
                    final StorageReference stemStorageRef = mStorage.child("mushroom_photos").child(time).child("stem").child(stemTime);
                    Task uploadTask = stemStorageRef.putFile(Uri.fromFile(photo));
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return stemStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String currTime = String.valueOf(new Date().getTime());
                                String stemURL = task.getResult().toString();
                                dbRef.child("mushroom_photos").child(time).child("stem").child(currTime)
                                        .setValue(stemURL);
                            }
                        }
                    });

                }
            }
            if (!TakePhotosActivity.getPaths().get("veilRing").isEmpty()) {
                for (String path : TakePhotosActivity.getPaths().get("veilRing")) {
                    File photo = new File(path);
                    String veilRingTime = String.valueOf(new Date().getTime());
                    final StorageReference veilRingStorageRef = mStorage.child("mushroom_photos").child(time).child("veilRing").child(veilRingTime);
                    Task uploadTask = veilRingStorageRef.putFile(Uri.fromFile(photo));
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return veilRingStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String currTime = String.valueOf(new Date().getTime());
                                String veilRingURL = task.getResult().toString();
                                dbRef.child("mushroom_photos").child(time).child("veilRing").child(currTime)
                                        .setValue(veilRingURL);
                            }
                        }
                    });
                }
            }
            if (!TakePhotosActivity.getPaths().get("other").isEmpty()) {
                int i = 0;
                for (String path : TakePhotosActivity.getPaths().get("other")) {
                    File photo = new File(path);
                    String otherTime = String.valueOf(new Date().getTime());
                    final StorageReference otherStorageRef = mStorage.child("mushroom_photos").child(time).child("other").child(otherTime);
                    Task uploadTask = otherStorageRef.putFile(Uri.fromFile(photo));
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return otherStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String currTime = String.valueOf(new Date().getTime());
                                String otherURL = task.getResult().toString();
                                dbRef.child("mushroom_photos").child(time).child("other").child(currTime)
                                        .setValue(otherURL);
                            }
                        }
                    });

                }
            }
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            toast("Permission to access GPS denied");
            return;
        }

        SingleShotLocationProvider.requestSingleUpdate(this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        mushroomLocation = new LatLng(location.latitude, location.longitude);

                    }
                });
    }


}