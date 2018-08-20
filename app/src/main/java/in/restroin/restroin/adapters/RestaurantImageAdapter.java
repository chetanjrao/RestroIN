package in.restroin.restroin.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;

public class RestaurantImageAdapter extends RecyclerView.Adapter<RestaurantImageAdapter.ViewHolder> {

    private List<String> images;

    public RestaurantImageAdapter(List<String> images){
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Images = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_restaurant_image, viewGroup, false);
        return new ViewHolder(Images);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String path = images.get(i).replaceAll("^\"|\"$", "");
        String image_location = "http://restroin.in/" + path;
        Uri image_path = Uri.parse(image_location);
        Picasso.get().load(image_path).into(viewHolder.restaurant_image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurant_image;
        public ViewHolder(View itemView) {
            super(itemView);
            restaurant_image = (ImageView) itemView.findViewById(R.id.restaurant_image);
        }
    }
}
