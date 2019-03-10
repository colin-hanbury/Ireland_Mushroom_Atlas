package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassifierResultAdapter  extends  RecyclerView.Adapter<ClassifierResultAdapter.TextViewHolder> {


    private final Context context;
    private final ArrayList<String> values;
    private String current;
    private String url;

    public ClassifierResultAdapter (Context context, ArrayList<String> values) {
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public ClassifierResultAdapter.TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.classifier_result_item, viewGroup, false);
        return new ClassifierResultAdapter.TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassifierResultAdapter.TextViewHolder textViewHolder, int i) {
        current = values.get(i);
        url = "No available website";
        if(current.contains("boletus edulis")){
            url = "https://en.wikipedia.org/wiki/Boletus_edulis";
        }
        else  if (current.contains("calocybe gambosa")){
            url = "https://en.wikipedia.org/wiki/Calocybe_gambosa";
        }
        else  if (current.contains("cantharellus cibarius")){
            url = "https://en.wikipedia.org/wiki/Cantharellus_cibarius";
        }
        else  if (current.contains("clavulina cristata")){
            url = "https://en.wikipedia.org/wiki/Clavulina_cristata";
        }
        else  if (current.contains("craterellus cornucopioides")){
            url = "https://en.wikipedia.org/wiki/Craterellus_cornucopioides";
        }
        else  if (current.contains("hygrocybe miniata")){
            url = "https://en.wikipedia.org/wiki/Hygrocybe_miniata";
        }
        else  if (current.contains("amanita muscaria")){
            url = "https://en.wikipedia.org/wiki/Amanita_muscaria";
        }
        else  if (current.contains("amanita phalloides")){
            url = "https://en.wikipedia.org/wiki/Amanita_phalloides";
        }
        else  if (current.contains("psilocybe semilanceata")){
            url = "https://en.wikipedia.org/wiki/Psilocybe_semilanceata";
        }
        textViewHolder.textValue.setText(current);
        textViewHolder.wikiLink.setText(url);
        textViewHolder.wikiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textValue;
        public TextView wikiLink;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textValue = itemView.findViewById(R.id.textViewClassifierResult);
            this.wikiLink = itemView.findViewById(R.id.wikiLink);
        }
    }
}
