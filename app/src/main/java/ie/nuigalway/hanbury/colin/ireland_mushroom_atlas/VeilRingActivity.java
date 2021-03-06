package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class VeilRingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static ArrayList<String> attributesList;
    private static HashMap<String, String> attributesMap;
    private Button saveAndReturn;
    private Dialog ringTypeTypeDialog;
    private Button ringTypeClose;
    private Button ringTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veil_ring);

        ringTypeButton = findViewById(R.id.ringTypesButton);
        ringTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpRingTypes();
            }
        });

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();

        saveAndReturn = findViewById(R.id.buttonSaveAndReturnVeilRingFeatures);
        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner veilType = findViewById(R.id.spinnerVeilType);
        Spinner veilColour = findViewById(R.id.spinnerVeilColour);
        Spinner ringQuantity = findViewById(R.id.spinnerRingQuantity);
        Spinner ringType = findViewById(R.id.spinnerRingType);

        ArrayAdapter<String> veilTypeAdapter = new ArrayAdapter<String>(
                VeilRingActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.veilTypes));
        veilTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veilType.setAdapter(veilTypeAdapter);
        veilType.setOnItemSelectedListener(this);

        ArrayAdapter<String> veilColourAdapter = new ArrayAdapter<String>(
                VeilRingActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.veilColours));
        veilColourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veilColour.setAdapter(veilColourAdapter);
        veilColour.setOnItemSelectedListener(this);

        ArrayAdapter<String> ringQuantityAdapter = new ArrayAdapter<String>(
                VeilRingActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.ringQuantity));
        ringQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ringQuantity.setAdapter(ringQuantityAdapter);
        ringQuantity.setOnItemSelectedListener(this);

        ArrayAdapter<String> ringTypeAdapter = new ArrayAdapter<String>(
                VeilRingActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.ringTypes));
        ringTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ringType.setAdapter(ringTypeAdapter);
        ringType.setOnItemSelectedListener(this);
    }

    private void showPopUpRingTypes() {
        ringTypeTypeDialog = new Dialog(VeilRingActivity.this);
        ringTypeTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ringTypeTypeDialog.setContentView(R.layout.pop_up_ring_type);
        ringTypeClose = ringTypeTypeDialog.findViewById(R.id.ringTypesCloseButton);
        ringTypeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringTypeTypeDialog.cancel();
            }
        });
        ringTypeTypeDialog.show();
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
    }

    public static ArrayList<String> getAttributesList(){
        return attributesList;
    }
    public static HashMap<String, String> getAttributesMap(){
        return attributesMap;
    }
}
