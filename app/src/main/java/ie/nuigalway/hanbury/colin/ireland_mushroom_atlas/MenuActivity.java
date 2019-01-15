package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.FirebaseApp;


public class MenuActivity extends AppCompatActivity {

    private ImageButton mapButton;
    private ImageButton observationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FirebaseApp.initializeApp(this);
        mapButton = findViewById(R.id.imageButtonMushroomMap);
        observationButton = findViewById(R.id.imageButtonMakeObservation);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MapsActivity = new Intent(MenuActivity.this,
                        ie.nuigalway.hanbury.colin.ireland_mushroom_atlas.MapsActivity.class);
                startActivity(MapsActivity);
            }
        });
        observationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewObservation = new Intent(MenuActivity.this,
                        NewObservationActivity.class);
                startActivity(NewObservation);
            }
        });
    }
}
