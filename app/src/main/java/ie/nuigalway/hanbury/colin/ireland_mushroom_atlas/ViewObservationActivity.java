package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ViewObservationActivity extends AppCompatActivity {



    private DatabaseReference database;
    private DatabaseReference dbRefCap;
    //private DatabaseReference dbRefStem;
    private DatabaseReference dbRefGill;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observation);

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


        database = FirebaseDatabase.getInstance().getReference();
        //"1547635105459"
        dbRefCap = database.child("Mushroom Attributes:").child(observationID).child("Cap").getRef();

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


        dbRefGill = database.child("Mushroom Attributes:").child(observationID).child("Gill").getRef();
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

        dbRefGill = database.child("Mushroom Attributes:").child(observationID).child("Stem").getRef();
        dbRefGill.addValueEventListener(new ValueEventListener() {
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
                dbRefGill.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRefGill = database.child("Mushroom Attributes:").child(observationID).child("Gill").getRef();
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

        dbRefGill = database.child("Mushroom Attributes:").child(observationID).child("Gill").getRef();
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
    }
}
