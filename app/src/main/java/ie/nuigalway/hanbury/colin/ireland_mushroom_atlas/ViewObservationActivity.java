package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ViewObservationActivity extends AppCompatActivity{

    private DatabaseReference database;

    private DatabaseReference dbRefCap;
    private DatabaseReference dbRefGill;
    private DatabaseReference dbRefStem;
    private DatabaseReference dbRefVeilRing;
    private DatabaseReference dbRefOther;
    private String observationID;
    private String imageID;
    private RecyclerView recyclerViewCap;
    private RecyclerView recyclerViewGill;
    private RecyclerView recyclerViewStem;
    private RecyclerView recyclerViewVeilRing;
    private RecyclerView recyclerViewOther;
    private ImageAdapter imageAdapter;
    private ArrayList<String> capURLs;
    private ArrayList<String> gillURLs;
    private ArrayList<String> stemURLs;
    private ArrayList<String> veilRingURLs;
    private ArrayList<String> otherURLs;
    private DatabaseReference dbRefCapPhoto;
    private DatabaseReference dbRefGillPhoto;
    private DatabaseReference dbRefStemPhoto;
    private DatabaseReference dbRefVeilRingPhoto;
    private DatabaseReference dbRefOtherPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observation);
        recyclerViewCap = findViewById(R.id.recyclerViewCap);
        recyclerViewCap.setHasFixedSize(true);
        recyclerViewCap.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGill = findViewById(R.id.recyclerViewGill);
        recyclerViewGill.setHasFixedSize(true);
        recyclerViewGill.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStem = findViewById(R.id.recyclerViewStem);
        recyclerViewStem.setHasFixedSize(true);
        recyclerViewStem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVeilRing = findViewById(R.id.recyclerViewVeilRing);
        recyclerViewVeilRing.setHasFixedSize(true);
        recyclerViewVeilRing.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOther = findViewById(R.id.recyclerViewOther);
        recyclerViewOther.setHasFixedSize(true);
        recyclerViewOther.setLayoutManager(new LinearLayoutManager(this));

        capURLs = new ArrayList<>();
        gillURLs = new ArrayList<>();
        stemURLs = new ArrayList<>();
        veilRingURLs = new ArrayList<>();
        otherURLs = new ArrayList<>();

        final TextView capColour = (TextView) findViewById(R.id.textViewObserveCapColourVal);
        final TextView capShape = (TextView) findViewById(R.id.textViewObserveCapShapeVal);
        final TextView capSurface = (TextView) findViewById(R.id.textViewObserveCapSurfaceVal);

        final TextView gillAttachment = (TextView) findViewById(R.id.textViewObserveGillAttachmentVal);
        final TextView gillSpacing = (TextView) findViewById(R.id.textViewObserveGillSpacingVal);
        final TextView gillSize = (TextView) findViewById(R.id.textViewObserveGillSizeVal);
        final TextView gillColour = (TextView) findViewById(R.id.textViewObserveGillColourVal);

        final TextView stalkShape = (TextView) findViewById(R.id.textViewObserveStalkShapeVal);
        final TextView stalkRoot = (TextView) findViewById(R.id.textViewObserveStalkRootVal);
        final TextView stalkSurface = (TextView) findViewById(R.id.textViewObserveStalkSurfaceVal);
        final TextView stalkColour = (TextView) findViewById(R.id.textViewObserveStalkColourVal);

        final TextView veilType = (TextView) findViewById(R.id.textViewObserveVeilTypeVal);
        final TextView veilColour = (TextView) findViewById(R.id.textViewObserveVeilColourVal);
        final TextView ringQuantity = (TextView) findViewById(R.id.textViewObserveRingQuantityVal);
        final TextView ringType = (TextView) findViewById(R.id.textViewObserveRingTypeVal);

        final TextView bruises = (TextView) findViewById(R.id.textViewObserveBruisesVal);
        final TextView odor = (TextView) findViewById(R.id.textViewObserveOdorVal);
        final TextView population = (TextView) findViewById(R.id.textViewObservePopulationVal);
        final TextView habitat = (TextView) findViewById(R.id.textViewObserveHabitatVal);

        Bundle bundle = getIntent().getExtras();
        observationID = bundle.getString("id");

        database = FirebaseDatabase.getInstance().getReference();

        dbRefCapPhoto = database.child("mushroom_photos").child(observationID).child("cap").getRef();
        dbRefGillPhoto = database.child("mushroom_photos").child(observationID).child("gill").getRef();
        dbRefStemPhoto = database.child("mushroom_photos").child(observationID).child("stem").getRef();
        dbRefVeilRingPhoto = database.child("mushroom_photos").child(observationID).child("veilRing").getRef();
        dbRefOtherPhoto = database.child("mushroom_photos").child(observationID).child("other").getRef();

        dbRefCapPhoto.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                   imageID = capSnapshot.getValue(String.class);
                   capURLs.add(imageID);
               }
               imageAdapter = new ImageAdapter(ViewObservationActivity.this, capURLs);
               recyclerViewCap.setAdapter(imageAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefGillPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    imageID = capSnapshot.getValue(String.class);
                    gillURLs.add(imageID);
                }
                imageAdapter = new ImageAdapter(ViewObservationActivity.this, gillURLs);
                recyclerViewGill.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefStemPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    imageID = capSnapshot.getValue(String.class);
                    stemURLs.add(imageID);
                }
                imageAdapter = new ImageAdapter(ViewObservationActivity.this, stemURLs);
                recyclerViewStem.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefVeilRingPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    imageID = capSnapshot.getValue(String.class);
                    veilRingURLs.add(imageID);
                }
                imageAdapter = new ImageAdapter(ViewObservationActivity.this, veilRingURLs);
                recyclerViewVeilRing.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefOtherPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    imageID = capSnapshot.getValue(String.class);
                    otherURLs.add(imageID);
                }
                imageAdapter = new ImageAdapter(ViewObservationActivity.this, otherURLs);
                recyclerViewOther.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });



        dbRefCap = database.child("mushroom_attributes:").child(observationID).child("cap").getRef();

        dbRefCap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    String cap = capSnapshot.getValue(String.class);
                    String key = capSnapshot.getKey().toString();
                    if (cap != null) {

                        if (key.contains("Cap_Colour")) {
                            capColour.setText(cap);
                        }
                        if (key.contains("Cap_Shape")) {
                            capShape.setText(cap);
                        }
                        if (key.contains("Cap_Surface")) {
                            capSurface.setText(cap);
                        }
                    }
                }
                dbRefCap.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        dbRefGill = database.child("mushroom_attributes:").child(observationID).child("gill").getRef();
        dbRefGill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    String gill = capSnapshot.getValue(String.class);
                    String key = capSnapshot.getKey().toString();
                    if (gill != null) {

                        if (key.contains("Gill_Attachment")) {
                            gillAttachment.setText(gill);
                        }
                        if (key.contains("Gill_Spacing")) {
                            gillSpacing.setText(gill);
                        }
                        if (key.contains("Gill_Size")) {
                            gillSize.setText(gill);
                        }
                        if (key.contains("Gill_Colour")) {
                            gillColour.setText(gill);
                        }
                    }
                }
                dbRefGill.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRefStem = database.child("mushroom_attributes:").child(observationID).child("stem").getRef();
        dbRefStem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    String stem = capSnapshot.getValue(String.class);
                    String key = capSnapshot.getKey().toString();
                    if (stem != null) {

                        if (key.contains("Stalk_Shape")) {
                            stalkShape.setText(stem);
                        }
                        if (key.contains("Stalk_Root")) {
                            stalkRoot.setText(stem);
                        }
                        if (key.contains("Stalk_Surface")) {
                            stalkSurface.setText(stem);
                        }
                        if (key.contains("Stalk_Colour")) {
                            stalkColour.setText(stem);
                        }
                    }
                }
                dbRefStem.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRefVeilRing = database.child("mushroom_attributes:").child(observationID).child("veil_ring").getRef();
        dbRefVeilRing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    String veilRing = capSnapshot.getValue(String.class);
                    String key = capSnapshot.getKey().toString();
                    if (veilRing != null) {

                        if (key.contains("Veil_Type")) {
                            veilType.setText(veilRing);
                        }
                        if (key.contains("Veil_Colour")) {
                            veilColour.setText(veilRing);
                        }
                        if (key.contains("Ring_Quantity")) {
                            ringQuantity.setText(veilRing);
                        }
                        if (key.contains("Ring_Type")) {
                            ringType.setText(veilRing);
                        }
                    }
                }
                dbRefVeilRing.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRefOther = database.child("mushroom_attributes:").child(observationID).child("other").getRef();
        dbRefOther.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot capSnapshot : dataSnapshot.getChildren()) {
                    String other = capSnapshot.getValue(String.class);
                    String key = capSnapshot.getKey().toString();
                    if (other != null) {
                        if (key.contains("Bruises")) {
                            bruises.setText(other);
                        }
                        if (key.contains("Odor")) {
                            odor.setText(other);
                        }
                        if (key.contains("Population")) {
                            population.setText(other);
                        }
                        if (key.contains("Habitat")) {
                            habitat.setText(other);
                        }
                    }
                }
                dbRefOther.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
