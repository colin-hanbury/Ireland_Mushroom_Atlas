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

public class StemFeaturesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static ArrayList<String> attributesList;
    private static HashMap<String, String> attributesMap;
    private Button saveAndReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stem_features);

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();


        saveAndReturn = findViewById(R.id.buttonSaveAndReturnStemFeatures);
        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Spinner stalkShape = findViewById(R.id.spinnerStalkShape);
        Spinner stalkRoot = findViewById(R.id.spinnerStalkRoot);
        Spinner stalkSurface = findViewById(R.id.spinnerStalkSurface);
        Spinner stalkColour = findViewById(R.id.spinnerStalkColour);

        ArrayAdapter<String> stalkShapeAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stalkShapes));
        stalkShapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stalkShape.setAdapter(stalkShapeAdapter);
        stalkShape.setOnItemSelectedListener(this);

        ArrayAdapter<String> stalkRootAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stalkRoots));
        stalkRootAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stalkRoot.setAdapter(stalkRootAdapter);
        stalkRoot.setOnItemSelectedListener(this);

        ArrayAdapter<String> stalkSurfaceAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stalkSurfaces));
        stalkSurfaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stalkSurface.setAdapter(stalkSurfaceAdapter);
        stalkSurface.setOnItemSelectedListener(this);

        ArrayAdapter<String> stalkColourAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stalkColours));
        stalkColourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stalkColour.setAdapter(stalkColourAdapter);
        stalkColour.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getSelectedItem().toString();
        if (!item.contains("Select")) {
            String tag = parent.getTag().toString();
            boolean newTag = true;
            //check if item is new or just being modified
            for (String currentTag : attributesList) {
                if (currentTag == tag) {
                    newTag = false;
                }
            }
            Toast.makeText(this, item + " selected from " + tag, Toast.LENGTH_SHORT).show();
            attributesMap.put(tag, item);
            if (newTag == true) {
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