package in.restroin.restroin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.restroin.restroin.R;

public class AmenitiesGridAdapter extends BaseAdapter {

    private List<String> amenities;
    private Context context;

    public AmenitiesGridAdapter(List<String> amenities, Context context){
        this.amenities = amenities;
        this.context = context;
    }

    @Override
    public int getCount() {
        return amenities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View AmenitiesView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            AmenitiesView = new View(context);
            assert inflater != null;
            AmenitiesView = inflater.inflate(R.layout.layout_amenity_view, parent, false);
            TextView cusine_name = (TextView) AmenitiesView.findViewById(R.id.amenity_name);
            cusine_name.setText(amenities.get(position));
        } else {
            AmenitiesView = (View) convertView;
        }
        return AmenitiesView;
    }
}
