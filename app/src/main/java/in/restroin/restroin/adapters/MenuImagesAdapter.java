package in.restroin.restroin.adapters;

import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;

public class MenuImagesAdapter extends RecyclerView.Adapter<MenuImagesAdapter.ViewHolder>{
    private List<String> menu_images;

    public MenuImagesAdapter(List<String> menu_images){
        this.menu_images = menu_images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_image_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = menu_images.get(position).replaceAll("^\"|\"$", "");
        String image_location = "https://www.restroin.in/" + path;
        Uri image_path = Uri.parse(image_location);
        Picasso.get().load(image_path).into(holder.menu_image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Log.e("RetrofitImageLog", "Error in loading image. The error is:  " + e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu_images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView menu_image;
        public ViewHolder(View itemView) {
            super(itemView);
            menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
        }
    }
}
