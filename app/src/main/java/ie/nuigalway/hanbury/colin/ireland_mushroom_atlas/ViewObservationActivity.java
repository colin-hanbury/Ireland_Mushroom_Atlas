package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class ViewObservationActivity extends AppCompatActivity{

    private DatabaseReference database;
    private DatabaseReference dbRefCap;
    private DatabaseReference dbRefGill;
    private DatabaseReference dbRefStem;
    private DatabaseReference dbRefVeilRing;
    private DatabaseReference dbRefOther;

    private String observationID;
    private String imageID;

    private RecyclerView recyclerViewGeneric;
    private RecyclerView recyclerViewCap;
    private RecyclerView recyclerViewGill;
    private RecyclerView recyclerViewStem;
    private RecyclerView recyclerViewVeilRing;
    private RecyclerView recyclerViewOther;
    private RecyclerView recyclerViewCapText;
    private RecyclerView recyclerViewGillText;
    private RecyclerView recyclerViewStemText;
    private RecyclerView recyclerViewVeilRingText;
    private RecyclerView recyclerViewOtherText;
    private RecyclerView recyclerViewComments;

    private ImageAdapter imageAdapter;
    private TextAdapter textAdapter;

    private ArrayList<String> genericURLs;
    private ArrayList<String> capURLs;
    private ArrayList<String> gillURLs;
    private ArrayList<String> stemURLs;
    private ArrayList<String> veilRingURLs;
    private ArrayList<String> otherURLs;

    private DatabaseReference dbRefGenericPhoto;
    private DatabaseReference dbRefCapPhoto;
    private DatabaseReference dbRefGillPhoto;
    private DatabaseReference dbRefStemPhoto;
    private DatabaseReference dbRefVeilRingPhoto;
    private DatabaseReference dbRefOtherPhoto;
    private DatabaseReference dbRefObserver;
    private DatabaseReference dbRefComments;

    private TextView observerName;
    private EditText newComment;
    private Button postButton;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private boolean initialTouch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observation);
        initialTouch = true;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        observerName = findViewById(R.id.displayObserverName);
        newComment = findViewById(R.id.newCommentInput);
        newComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(initialTouch){
                    newComment.setText("");
                }
                initialTouch = false;
                return false;
            }
        });
        postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.valueOf(new Date().getTime());
                String username = user.getEmail().split("@", 2)[0];
                username = username.replace(".", "");
                database.child("comments").child(observationID).child(time).child(username)
                        .setValue(newComment.getText().toString());
                initialTouch = true;
                newComment.setText("Write a comment...");
            }
        });
        recyclerViewGeneric = findViewById(R.id.recyclerViewGeneric);
        recyclerViewGeneric.setHasFixedSize(true);
        recyclerViewGeneric.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCap = findViewById(R.id.recyclerViewCap);
        recyclerViewCap.setHasFixedSize(true);
        recyclerViewCap.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGill = findViewById(R.id.recyclerViewGill);
        recyclerViewGill.setHasFixedSize(true);
        recyclerViewGill.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCapText = findViewById(R.id.recyclerViewCapText);
        recyclerViewCapText.setHasFixedSize(true);
        recyclerViewCapText.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGillText = findViewById(R.id.recyclerViewGillText);
        recyclerViewGillText.setHasFixedSize(true);
        recyclerViewGillText.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStemText = findViewById(R.id.recyclerViewStemText);
        recyclerViewStemText.setHasFixedSize(true);
        recyclerViewStemText.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVeilRingText = findViewById(R.id.recyclerViewVeilRingText);
        recyclerViewVeilRingText.setHasFixedSize(true);
        recyclerViewVeilRingText.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOtherText = findViewById(R.id.recyclerViewOtherText);
        recyclerViewOtherText.setHasFixedSize(true);
        recyclerViewOtherText.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGillText.setHasFixedSize(true);
        recyclerViewGillText.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStem = findViewById(R.id.recyclerViewStem);
        recyclerViewStem.setHasFixedSize(true);
        recyclerViewStem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVeilRing = findViewById(R.id.recyclerViewVeilRing);
        recyclerViewVeilRing.setHasFixedSize(true);
        recyclerViewVeilRing.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOther = findViewById(R.id.recyclerViewOther);
        recyclerViewOther.setHasFixedSize(true);
        recyclerViewOther.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setHasFixedSize(true);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        genericURLs = new ArrayList<>();
        capURLs = new ArrayList<>();
        gillURLs = new ArrayList<>();
        stemURLs = new ArrayList<>();
        veilRingURLs = new ArrayList<>();
        otherURLs = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        observationID = bundle.getString("id");



        dbRefObserver = database.child("user").child(observationID).getRef();
        dbRefObserver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = "John Smith";
                name = dataSnapshot.getValue(String.class);
                name = name.concat(":");
                observerName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefGenericPhoto = database.child("mushroom_photos").child(observationID).child("generic").getRef();
        dbRefCapPhoto = database.child("mushroom_photos").child(observationID).child("cap").getRef();
        dbRefGillPhoto = database.child("mushroom_photos").child(observationID).child("gill").getRef();
        dbRefStemPhoto = database.child("mushroom_photos").child(observationID).child("stem").getRef();
        dbRefVeilRingPhoto = database.child("mushroom_photos").child(observationID).child("veilRing").getRef();
        dbRefOtherPhoto = database.child("mushroom_photos").child(observationID).child("other").getRef();

        dbRefCap = database.child("mushroom_attributes:").child(observationID).child("cap").getRef();
        dbRefGill = database.child("mushroom_attributes:").child(observationID).child("gill").getRef();
        dbRefStem = database.child("mushroom_attributes:").child(observationID).child("stem").getRef();
        dbRefVeilRing = database.child("mushroom_attributes:").child(observationID).child("veil_ring").getRef();
        dbRefOther = database.child("mushroom_attributes:").child(observationID).child("other").getRef();

        dbRefComments = database.child("comments").child(observationID).getRef();

        dbRefGenericPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageID = snapshot.getValue(String.class);
                    genericURLs.add(imageID);
                }
                imageAdapter = new ImageAdapter(ViewObservationActivity.this, genericURLs);
                recyclerViewGeneric.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefCapPhoto.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   imageID = snapshot.getValue(String.class);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageID = snapshot.getValue(String.class);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageID = snapshot.getValue(String.class);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageID = snapshot.getValue(String.class);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageID = snapshot.getValue(String.class);
                    otherURLs.add(imageID);
                }
                imageAdapter = new ImageAdapter(ViewObservationActivity.this, otherURLs);
                recyclerViewOther.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


        dbRefCap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> capValues = new ArrayList<>();
                ArrayList<String> capTitles = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String cap = snapshot.getValue(String.class);
                    String key = snapshot.getKey().toString();
                    key = key.concat(":");
                    capValues.add(cap);
                    capTitles.add(key);
                }
                textAdapter = new TextAdapter(ViewObservationActivity.this, capTitles, capValues);
                recyclerViewCapText.setAdapter(textAdapter);
                dbRefCap.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        dbRefGill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> gillValues = new ArrayList<>();
                ArrayList<String> gillTitles = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String gill = snapshot.getValue(String.class);
                    String key = snapshot.getKey().toString();
                    key = key.concat(":");
                    gillValues.add(gill);
                    gillTitles.add(key);
                }
                textAdapter = new TextAdapter(ViewObservationActivity.this, gillTitles, gillValues);
                recyclerViewGillText.setAdapter(textAdapter);
                dbRefGill.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dbRefStem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> stemValues = new ArrayList<>();
                ArrayList<String> stemTitles = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String stem = snapshot.getValue(String.class);
                    String key = snapshot.getKey().toString();
                    key = key.concat(":");
                    stemValues.add(stem);
                    stemTitles.add(key);
                }
                textAdapter = new TextAdapter(ViewObservationActivity.this, stemTitles, stemValues);
                recyclerViewStemText.setAdapter(textAdapter);
                dbRefStem.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


        dbRefVeilRing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> veilRingValues = new ArrayList<>();
                ArrayList<String> veilRingTitles = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String veilRing = snapshot.getValue(String.class);
                    String key = snapshot.getKey().toString();
                    key = key.concat(":");
                    veilRingValues.add(veilRing);
                    veilRingTitles.add(key);
                }
                textAdapter = new TextAdapter(ViewObservationActivity.this, veilRingTitles, veilRingValues);
                recyclerViewVeilRingText.setAdapter(textAdapter);
                dbRefVeilRing.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRefOther.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> otherValues = new ArrayList<>();
                ArrayList<String> otherTitles = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String other = snapshot.getValue(String.class);
                    String key = snapshot.getKey().toString();
                    key = key.concat(":");
                    otherValues.add(other);
                    otherTitles.add(key);
                }
                textAdapter = new TextAdapter(ViewObservationActivity.this, otherTitles, otherValues);
                recyclerViewOtherText.setAdapter(textAdapter);
                dbRefOther.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbRefComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> comments = new ArrayList<>();
                ArrayList<String> commentators = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot ss: snapshot.getChildren()) {
                        String comment = ss.getValue(String.class);
                        String commentator = ss.getKey().toString();
                        commentator = commentator.concat(":");
                        comments.add(comment);
                        commentators.add(commentator);
                    }
                }
                textAdapter = new TextAdapter(ViewObservationActivity.this, commentators, comments);
                recyclerViewComments.setAdapter(textAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
