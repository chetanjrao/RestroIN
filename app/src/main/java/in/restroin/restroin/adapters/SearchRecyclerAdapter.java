package in.restroin.restroin.adapters;

import android.media.Rating;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.models.HangoutRestaurants;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    private List<HangoutRestaurants> restaurants;

    public SearchRecyclerAdapter(List<HangoutRestaurants> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_restaurant_snippet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.restaurant_rating.setRating(Float.parseFloat(restaurants.get(position).getRestaurant_rating()));
        holder.restaurant_offers.setText(restaurants.get(position).getRestaurant_coupon_selected());
        holder.restaurant_region.setText(restaurants.get(position).getRestaurant_region());
        holder.restaurant_name.setText(restaurants.get(position).getRestaurant_name());
        Uri image = Uri.parse("https://www.restroin.in/" + restaurants.get(position).getFront_image());
        Picasso.get().load(image).into(holder.restaurant_image);
    }

    @Override
    public int getItemCount() {
        if (restaurants != null){
         return restaurants.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurant_image;
        TextView restaurant_name, restaurant_region, restaurant_offers;
        RatingBar restaurant_rating;
        public ViewHolder(View itemView) {
            super(itemView);
            restaurant_image = (ImageView) itemView.findViewById(R.id.restaurant_image);
            restaurant_name = (TextView)itemView.findViewById(R.id.restaurant_name);
            restaurant_region = (TextView) itemView.findViewById(R.id.restaurant_location);
            restaurant_offers = (TextView) itemView.findViewById(R.id.restaurant_offers);
            restaurant_rating = (RatingBar) itemView.findViewById(R.id.restaurant_rating);
        }
    }
}
