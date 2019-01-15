package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CapFeaturesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static ArrayList<String> attributesList;
    private static HashMap<String, String> attributesMap;
    private Button saveAndReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_features);

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();

        saveAndReturn = findViewById(R.id.buttonSaveAndReturnCapFeatures);
        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner capShape = findViewById(R.id.spinnerCapShape);
        Spinner capSurface = findViewById(R.id.spinnerCapSurface);
        Spinner capColour = findViewById(R.id.spinnerCapColour);

        ArrayAdapter<String> capShapeAdapter = new ArrayAdapter<String>(
                CapFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.capShapes));
        capShapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capShape.setAdapter(capShapeAdapter);
        capShape.setOnItemSelectedListener(this);

        ArrayAdapter<String> capSurfaceAdapter = new ArrayAdapter<String>(
                CapFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.capSurfaces));
        capSurfaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capSurface.setAdapter(capSurfaceAdapter);
        capSurface.setOnItemSelectedListener(this);

        ArrayAdapter<String> capColourAdapter = new ArrayAdapter<String>(
                CapFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.capColours));
        capColourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capColour.setAdapter(capColourAdapter);
        capColour.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getSelectedItem().toString();
        if(!item.contains("Select")){
            String tag = parent.getTag().toString();
            boolean newTag = true;
            //check if item is new or just being modified
            for (String currentTag: attributesList) {
                if(currentTag == tag){
                    newTag = false;
                }
            }
            Toast.makeText(this, item + " selected from " + tag, Toast.LENGTH_SHORT).show();
            attributesMap.put(tag, item);
            if(newTag == true) {
                attributesList.add(tag);
            }
        }
        else {
            String tag = parent.getTag().toString();
            attributesMap.remove(tag);
            attributesList.remove(tag);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }



    public static ArrayList<String> getAttributesList(){
        return attributesList;
    }
    public static HashMap<String, String> getAttributesMap(){
        return attributesMap;
    }
}