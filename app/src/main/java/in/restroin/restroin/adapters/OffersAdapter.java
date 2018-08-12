package in.restroin.restroin.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import in.restroin.restroin.R;
import java.util.List;
import in.restroin.restroin.models.Offers;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    List<Offers> offers;
    Context context;

    public OffersAdapter(List<Offers> offers, Context context){
        this.offers = offers;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView offer_image;
        TextView offer;
        String offer_code;

        public ViewHolder(View itemView) {
            super(itemView);
            offer_image = (ImageView) itemView.findViewById(R.id.offer_image);
            offer = (TextView) itemView.findViewById(R.id.offer);
            offer_code = null;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View OfferView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_offers_banner, parent, false);
        return new ViewHolder(OfferView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image_location = "http://restroin.in/"  + offers.get(position).getImage();
        Uri image_path = Uri.parse(image_location);
        Picasso.get().load(image_path).into(holder.offer_image);
        holder.offer.setText(offers.get(position).getOffer());
        holder.offer_code = offers.get(position).getOffer_filter_code();
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

}
