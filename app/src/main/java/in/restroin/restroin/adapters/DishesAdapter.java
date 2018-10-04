package in.restroin.restroin.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.models.RestaurantDishesModel;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {

    private List<RestaurantDishesModel> locations;
    private Context context;

    public DishesAdapter(List<RestaurantDishesModel> locations, Context context){
        this.context = context;
        this.locations = locations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView menu_image;
        TextView menu_name, menu_price;

        public ViewHolder(View itemView){
            super(itemView);
            menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
            menu_name = (TextView) itemView.findViewById(R.id.menu);
            menu_price = (TextView) itemView.findViewById(R.id.menu_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View PopularLocationsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_popular_dishes, parent, false);
        return new ViewHolder(PopularLocationsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Typeface raleway = Typeface.createFromAsset(context.getAssets(), "font/raleway.ttf");
        String path = locations.get(i).getMenu_image();
        String location_image = "https://www.restroin.in/" + path;
        Uri location_image_url = Uri.parse(location_image);
        Picasso.get().load(location_image_url).into(viewHolder.menu_image);
        viewHolder.menu_name.setText(locations.get(i).getMenu_name());
        viewHolder.menu_name.setTypeface(raleway);
        viewHolder.menu_price.setTypeface(raleway);
        viewHolder.menu_price.setText("\u20B9 " + locations.get(i).getMenu_price() + "/-");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
