package in.restroin.restroin.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.models.CusineGridModel;

public class CusinesGridAdapter extends BaseAdapter {

    private List<CusineGridModel> cusines;
    private Context context;

    public CusinesGridAdapter(List<CusineGridModel> cusines, Context context){
        this.cusines = cusines;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cusines.size();
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
        View CusinesView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            CusinesView = new View(context);
            assert inflater != null;
            CusinesView = inflater.inflate(R.layout.layout_cusines_grid, parent, false);
            TextView cusine_name = (TextView) CusinesView.findViewById(R.id.name_of_cusine);
            TextView cusine_restaurants_count = (TextView) CusinesView.findViewById(R.id.total_restaurants_with_cusine);
            ImageView cusine_image = (ImageView) CusinesView.findViewById(R.id.cusine_image);
            cusine_name.setText(cusines.get(position).getCusine_name());
            cusine_restaurants_count.setText(cusines.get(position).getCusine_count());
            String path = cusines.get(position).getCusine_image().replaceAll("^\"|\"$", "");
            String cusine_image_path = "http://restroin.in/" + path;
            Uri cusine_image_url = Uri.parse(cusine_image_path);
            Picasso.get().load(cusine_image_url).into(cusine_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            CusinesView = (View) convertView;
        }
        return CusinesView;
    }
}
