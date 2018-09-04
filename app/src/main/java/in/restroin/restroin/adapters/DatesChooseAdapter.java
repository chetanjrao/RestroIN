package in.restroin.restroin.adapters;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.ChartData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.restroin.restroin.R;
import in.restroin.restroin.utils.SmoothCheckBox;

public class DatesChooseAdapter extends RecyclerView.Adapter<DatesChooseAdapter.ViewHolder>{

    private List<String> dates;
    private int CheckedCardViewPosition;
    private RecyclerView recyclerView;

    public DatesChooseAdapter(List<String> dates){
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View DatesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dates_view, parent, false);
        return new ViewHolder(DatesView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.date_of_booking.setText(dates.get(position));
        holder.layout_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.smoothCheckBox.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_of_booking;
        RelativeLayout layout_dates;
        SmoothCheckBox smoothCheckBox;
        public ViewHolder(View itemView) {
            super(itemView);
            smoothCheckBox = (SmoothCheckBox) itemView.findViewById(R.id.date_smooth_checkbox);
            smoothCheckBox.setClickable(false);
            smoothCheckBox.setFocusable(false);
            layout_dates = (RelativeLayout) itemView.findViewById(R.id.date_select_cardView);
            date_of_booking = (TextView) itemView.findViewById(R.id.date_of_booking_choose);
        }
    }
}