package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewObservationActivity extends AppCompatActivity {

    private DatabaseReference database;

    private DatabaseReference dbRefCap;
    private DatabaseReference dbRefGill;
    private DatabaseReference dbRefStem;
    private DatabaseReference dbRefVeilRing;
    private DatabaseReference dbRefOther;

    private StorageReference storage;

    private StorageReference sRefCap;
    private StorageReference sRefGill;
    private StorageReference sRefStem;
    private StorageReference sRefVeilRing;
    private StorageReference sRefOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observation);

        /*
        ImageView imageCap = findViewById(R.id.imageViewCap);
        GridView capImages = findViewById(R.id.gridViewCap);
        ArrayAdapter<ImageView> capAdapter = new ArrayAdapter<ImageView>(ViewObservationActivity.this,
                android.R.layout.simple_list_item_1, getResources().get)
        capImages.setAdapter();
        */

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
        String observationID = bundle.getString("id");

        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference();

        sRefCap = storage.child("mushroom_photos").child(observationID).child("cap");
        //GlideApp.with(ViewObservationActivity.this).load(sRefCap).into(imageCap);

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
