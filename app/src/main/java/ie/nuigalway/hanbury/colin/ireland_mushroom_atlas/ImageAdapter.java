package ie.nuigalway.hanbury.colin.ireland_mushroom_atlas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private ArrayList<String> urls;

    public ImageAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, viewGroup, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ImageViewHolder imageViewHolder, int i) {
        if(urls.get(i) == null){
            Toast.makeText(context, "No url", Toast.LENGTH_LONG).show();
        }

        Picasso.get()
                .load(urls.get(i))
                .fit()
                .into(imageViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(View itemView){
            super(itemView);
                this.imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
