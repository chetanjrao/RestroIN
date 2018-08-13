package in.restroin.restroin.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.models.PopularRestaurants;

public class PopularRestaurantsAdapter extends RecyclerView.Adapter<PopularRestaurantsAdapter.ViewHolder> {
    private List<PopularRestaurants> popularRestaurants;
    private Context context;

    public PopularRestaurantsAdapter(List<PopularRestaurants> popularRestaurants, Context context){
        this.popularRestaurants = popularRestaurants;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView restaurant_image;
        TextView restaurant_name, price_for_two, restaurant_region;
        RatingBar restaurant_rating;
        public ViewHolder(View itemView){
            super(itemView);
            restaurant_image = (ImageView) itemView.findViewById(R.id.restaurant_image);
            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);
            restaurant_region = (TextView) itemView.findViewById(R.id.city_name_and_zone);
            price_for_two = (TextView) itemView.findViewById(R.id.price_for_two_of_restaurant);
            restaurant_rating = (RatingBar) itemView.findViewById(R.id.restaurant_rating);
        }
    }
    @NonNull
    @Override
    public PopularRestaurantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View popularRestaurantsView = LayoutInflater.from(context).inflate(R.layout.layout_restaurants_card, parent, false);
        return new ViewHolder(popularRestaurantsView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRestaurantsAdapter.ViewHolder holder, int position) {
        String path = popularRestaurants.get(position).getFront_image().replaceAll("^\"|\"$", "");
        String image_location = "http://restroin.in/" + path;
        Uri image_path = Uri.parse(image_location);
        Picasso.get().load(image_path).into(holder.restaurant_image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Log.e("Picasso:" , "" + e.getLocalizedMessage() + "Error Now: " + e.getMessage());
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(context, "URL : " +  image_location, Toast.LENGTH_SHORT).show();
        holder.restaurant_name.setText(popularRestaurants.get(position).getRestaurant_name());
        holder.restaurant_rating.setRating(Float.parseFloat(popularRestaurants.get(position).getRestaurant_rating()));
        holder.price_for_two.setText(popularRestaurants.get(position).getPrice_for_two());
        holder.restaurant_region.setText(popularRestaurants.get(position).getRestaurant_region());
    }

    @Override
    public int getItemCount() {
        return popularRestaurants.size();
    }
}
