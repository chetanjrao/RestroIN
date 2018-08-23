package in.restroin.restroin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.restroin.restroin.adapters.AmenitiesGridAdapter;
import in.restroin.restroin.adapters.RestaurantImageAdapter;
import in.restroin.restroin.interfaces.RestaurantClient;
import in.restroin.restroin.models.RestaurantModel;
import in.restroin.restroin.utils.MyGridView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class RestaurantViewActivity extends AppCompatActivity {

    private final static String RETROFIT_TAG = "RETROFIT_LOG";
    private static final int[] COLORS = {
            rgb("#c70000"), rgb("#e23100"), rgb("#ff9500"), rgb("#88ff00"), rgb("#26ca02")
    };

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://restroin.in/")
            .addConverterFactory(GsonConverterFactory.create());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final View view = getWindow().getDecorView().getRootView();
        final float[] scrollPos = new float[1];
        view.post(new Runnable() {
            @Override
            public void run() {
                scrollPos[0] = view.getScaleY();

                if (scrollPos[0] > 300) {
                    Toast.makeText(RestaurantViewActivity.this, "Done : " + scrollPos[0], Toast.LENGTH_SHORT).show();
                }
            }
        });
        final Button button = (Button) findViewById(R.id.book_now_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantViewActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });
        final NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.restaurant_nested_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RestaurantViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView images_recycler = (RecyclerView) findViewById(R.id.restaurant_images);
        final MyGridView myGridView = (MyGridView) findViewById(R.id.features_gridView);
        String id = getIntent().getStringExtra("restaurant_id");
        Toast.makeText(this, "ID IS: " + id, Toast.LENGTH_SHORT).show();
        images_recycler.setLayoutManager(layoutManager);
        Retrofit restaurant_retrofit = builder.build();
        final TextView restaurant_name = (TextView) findViewById(R.id.restaurant_name);
        final TextView restaurant_city = (TextView) findViewById(R.id.restaurant_city);
        final TextView restaurant_address = (TextView) findViewById(R.id.address_of_restaurant);
        final TextView restaurant_timing = (TextView) findViewById(R.id.timing_of_restaurant);
        final RatingBar restaurant_rating = (RatingBar) findViewById(R.id.restaurant_rating);
        final TextView cost_for_two = (TextView) findViewById(R.id.cost_for_two);
        RestaurantClient client = restaurant_retrofit.create(RestaurantClient.class);
        Call<RestaurantModel> call = client.getRestaurantData(id);
        call.enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantModel> call,@NonNull Response<RestaurantModel> response) {
                if(response.isSuccessful()){
                    List<String> restaurant_images = response.body().getFront_image();
                    List<String> restaurant_features = response.body().getRestaurant_features_amenities();
                    restaurant_name.setText(response.body().getRestaurant_name());
                    restaurant_city.setText(response.body().getCity_id());
                    restaurant_address.setText(" " +response.body().getRestaurant_address());
                    cost_for_two.setText(" \u20B9 " +response.body().getPrice_for_two() + " /-");
                    restaurant_rating.setRating(Float.parseFloat(response.body().getRestaurant_rating()));
                    restaurant_timing.setText(" " + response.body().getRestaurant_opening_time() + " - " + response.body().getRestaurant_closing_time());
                    AmenitiesGridAdapter amenitiesGridAdapter = new AmenitiesGridAdapter(restaurant_features,RestaurantViewActivity.this);
                    myGridView.setAdapter(amenitiesGridAdapter);
                    RestaurantImageAdapter adapter = new RestaurantImageAdapter(restaurant_images);
                    images_recycler.setAdapter(adapter);
                } else {
                    Toast.makeText(RestaurantViewActivity.this, "Something Went Wrong: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantModel> call,@NonNull Throwable t) {
                Toast.makeText(RestaurantViewActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
