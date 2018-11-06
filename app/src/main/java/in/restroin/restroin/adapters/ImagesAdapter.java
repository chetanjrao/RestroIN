package in.restroin.restroin.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    List<String>  images;

    public ImagesAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Uri imageUri = Uri.parse("https://www.restroin.in/" + images.get(position));
        Picasso.get().load(imageUri).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        if(images != null){
        if(position+1 == images.size()){
            Toast.makeText(holder.itemView.getContext(), "This is the last image", Toast.LENGTH_SHORT).show();
        } } else {
            Toast.makeText(holder.itemView.getContext(), "Sorry. No Images Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if(images != null){
        return images.size();} else {
            return  0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.ProgressBar);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
