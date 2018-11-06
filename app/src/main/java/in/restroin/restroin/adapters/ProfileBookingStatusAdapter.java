package in.restroin.restroin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.models.BookingStatusModel;

public class ProfileBookingStatusAdapter extends RecyclerView.Adapter<ProfileBookingStatusAdapter.ViewHolder> {

    ArrayList<BookingStatusModel> bookingStatusModels;

    public ProfileBookingStatusAdapter(ArrayList<BookingStatusModel> bookingStatusModels) {
        this.bookingStatusModels = bookingStatusModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_profile_dine_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.restaurant_name.setText(bookingStatusModels.get(position).getRestaurant_name());
        holder.visiting_dtae_time.setText(bookingStatusModels.get(position).getVisiting_date_time());
        holder.no_of_guests.setText(bookingStatusModels.get(position).getNo_of_guests());
        if(bookingStatusModels.get(position).getStatus().equals("Confirm")){
            holder.booking_status_image.setImageResource(R.drawable.ic_done);
            holder.status.setText("Confirmed");
        } else if(bookingStatusModels.get(position).getStatus().equals("Pending")){
            holder.booking_status_image.setImageResource(R.drawable.ic_pending);
        }
    }


    @Override
    public int getItemCount() {
        if(bookingStatusModels != null){
            return bookingStatusModels.size();
        } else {
            return  0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurant_name, visiting_dtae_time, no_of_guests, status;
        ImageView booking_status_image;
        public ViewHolder(View itemView) {
            super(itemView);
            booking_status_image = (ImageView) itemView.findViewById(R.id.booking_status_image);
            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);
            visiting_dtae_time = (TextView) itemView.findViewById(R.id.visiting_time);
            no_of_guests = (TextView) itemView.findViewById(R.id.no_of_guests);
            status = (TextView) itemView.findViewById(R.id.booking_status);
        }
    }
}
