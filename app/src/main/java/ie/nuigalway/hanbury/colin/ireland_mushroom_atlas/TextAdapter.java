package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TextAdapter extends  RecyclerView.Adapter<TextAdapter.TextViewHolder> {

    private final Context context;
    private final ArrayList<String> titles;
    private final ArrayList<String> values;

    public TextAdapter (Context context, ArrayList<String> titles, ArrayList<String> values) {
        this.context = context;
        this.titles = titles;
        this.values = values;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_item, viewGroup, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder textViewHolder, int i) {
        textViewHolder.textTitle.setText(titles.get(i));
        textViewHolder.textValue.setText(values.get(i));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textValue;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textTitle = itemView.findViewById(R.id.textViewTitle);
            this.textValue = itemView.findViewById(R.id.textViewValue);
        }
    }
}
