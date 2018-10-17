package in.restroin.restroin.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.utils.SmoothCheckBox;

public class TimeChooseAdapter extends RecyclerView.Adapter<TimeChooseAdapter.ViewHolder>{

    private List<String> time;
    private int CheckedCardViewPosition;
    private RecyclerView recyclerView;

    public TimeChooseAdapter(List<String> dates){
        this.time = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View DatesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_time_item, parent, false);
        return new ViewHolder(DatesView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.time_of_booking.setText(time.get(position));
        if (CheckedCardViewPosition == position){
            holder.smoothCheckBox.animate();
            holder.smoothCheckBox.setChecked(true);
        } else {
            holder.smoothCheckBox.animate();

            holder.smoothCheckBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_of_booking;
        RelativeLayout layout_time;
        SmoothCheckBox smoothCheckBox;
        ImageView background_image;
        public ViewHolder(View itemView) {
            super(itemView);
            background_image = (ImageView) itemView.findViewById(R.id.background_image);
            Uri path_to_assets = Uri.parse("https://www.restroin.in/developers/api/assets/back.jpg");
            Picasso.get().load(path_to_assets).into(background_image);
            smoothCheckBox = (SmoothCheckBox) itemView.findViewById(R.id.date_smooth_checkbox);
            smoothCheckBox.setClickable(false);
            smoothCheckBox.setFocusable(false);
            layout_time = (RelativeLayout) itemView.findViewById(R.id.date_select_cardView);
            time_of_booking = (TextView) itemView.findViewById(R.id.date_of_booking_choose);
            layout_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckedCardViewPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
