package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
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
    private Dialog stemRootTypeDialog;
    private Button stemRootTypeClose;
    private Button stemRootTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stem_features);

        stemRootTypeButton = findViewById(R.id.stemRootButton);
        stemRootTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpStemRootTypes();
            }
        });

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();


        saveAndReturn = findViewById(R.id.buttonSaveAndReturnStemFeatures);
        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Spinner stemShape = findViewById(R.id.spinnerStemShape);
        Spinner stemRoot = findViewById(R.id.spinnerStemRoot);
        Spinner stemSurface = findViewById(R.id.spinnerStemSurface);
        Spinner stemColour = findViewById(R.id.spinnerStemColour);

        ArrayAdapter<String> stemShapeAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stemShapes));
        stemShapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stemShape.setAdapter(stemShapeAdapter);
        stemShape.setOnItemSelectedListener(this);

        ArrayAdapter<String> stemRootAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stemRoots));
        stemRootAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stemRoot.setAdapter(stemRootAdapter);
        stemRoot.setOnItemSelectedListener(this);

        ArrayAdapter<String> stemSurfaceAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stemSurfaces));
        stemSurfaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stemSurface.setAdapter(stemSurfaceAdapter);
        stemSurface.setOnItemSelectedListener(this);

        ArrayAdapter<String> stemColourAdapter = new ArrayAdapter<String>(
                StemFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.stemColours));
        stemColourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stemColour.setAdapter(stemColourAdapter);
        stemColour.setOnItemSelectedListener(this);
    }

    private void showPopUpStemRootTypes() {
        stemRootTypeDialog = new Dialog(StemFeaturesActivity.this);
        stemRootTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        stemRootTypeDialog.setContentView(R.layout.pop_up_stem_root_type);
        stemRootTypeClose = stemRootTypeDialog.findViewById(R.id.stemRootTypesCloseButton);
        stemRootTypeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stemRootTypeDialog.cancel();
            }
        });
        stemRootTypeDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getSelectedItem().toString();
        if (!item.contains("Select")) {
            String tag = parent.getTag().toString();
            tag = tag.replace("_", " ");
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