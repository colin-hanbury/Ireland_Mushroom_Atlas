package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class OtherFeaturesActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private static ArrayList<String> attributesList;
    private static HashMap<String, String> attributesMap;
    private Button saveAndReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_features);

        attributesList = new ArrayList<>();
        attributesMap = new HashMap<>();

        saveAndReturn = findViewById(R.id.buttonSaveAndReturnOtherFeatures);
        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        Spinner bruises = findViewById(R.id.spinnerBruises);
        Spinner odor = findViewById(R.id.spinnerOdor);
        Spinner population = findViewById(R.id.spinnerPopulation);
        Spinner habitat = findViewById(R.id.spinnerHabitat);

        ArrayAdapter<String> bruisesAdapter = new ArrayAdapter<String>(
                OtherFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.bruises));
        bruisesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bruises.setAdapter(bruisesAdapter);
        bruises.setOnItemSelectedListener(this);

        ArrayAdapter<String> odorAdapter = new ArrayAdapter<String>(
                OtherFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.odor));
        odorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        odor.setAdapter(odorAdapter);
        odor.setOnItemSelectedListener(this);

        ArrayAdapter<String> populationAdapter = new ArrayAdapter<String>(
                OtherFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.populations));
        populationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        population.setAdapter(populationAdapter);
        population.setOnItemSelectedListener(this);

        ArrayAdapter<String> habitatAdapter = new ArrayAdapter<String>(
                OtherFeaturesActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.habitats));
        habitatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        habitat.setAdapter(habitatAdapter);
        habitat.setOnItemSelectedListener(this);

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