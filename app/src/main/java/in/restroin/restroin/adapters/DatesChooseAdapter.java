package in.restroin.restroin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.restroin.restroin.R;

public class DatesChooseAdapter extends RecyclerView.Adapter<DatesChooseAdapter.ViewHolder>{

    private List<String> dates;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date_of_booking.setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_of_booking;
        public ViewHolder(View itemView) {
            super(itemView);
            date_of_booking = (TextView) itemView.findViewById(R.id.date_of_booking_choose);
        }
    }
}
