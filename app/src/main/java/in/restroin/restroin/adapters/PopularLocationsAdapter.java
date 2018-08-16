package in.restroin.restroin.adapters;

import android.content.Context;
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
import in.restroin.restroin.models.PopularLocations;

public class PopularLocationsAdapter extends RecyclerView.Adapter<PopularLocationsAdapter.ViewHolder> {

    List<PopularLocations> locations;
    Context context;

    public PopularLocationsAdapter(List<PopularLocations> locations, Context context){
        this.context = context;
        this.locations = locations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView popular_location_image;
        RatingBar popular_location_rating;
        TextView popular_location_name, popular_location_restaurants;

        public ViewHolder(View itemView){
            super(itemView);
            popular_location_image = (ImageView) itemView.findViewById(R.id.popular_location_image);
            popular_location_rating = (RatingBar) itemView.findViewById(R.id.popular_location_rating);
            popular_location_name = (TextView) itemView.findViewById(R.id.popular_location);
            popular_location_restaurants = (TextView) itemView.findViewById(R.id.total_restaurants_in_location);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View PopularLocationsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_popular_locations, parent, false);
        return new ViewHolder(PopularLocationsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String path = locations.get(i).getLocation_image().replaceAll("^\"|\"$", "");
        String location_image = "http://restroin.in/" + path;
        Uri location_image_url = Uri.parse(location_image);
        Picasso.get().load(location_image_url).into(viewHolder.popular_location_image);
        viewHolder.popular_location_name.setText(locations.get(i).getLocation_name());
        viewHolder.popular_location_rating.setRating(Float.parseFloat(locations.get(i).getLocation_rating()));
        viewHolder.popular_location_restaurants.setText(locations.get(i).getLocations_restaurants() + " Restaurants");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
