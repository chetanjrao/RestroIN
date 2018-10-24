package in.restroin.restroin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import in.restroin.restroin.adapters.SearchRecyclerAdapter;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.HangoutRestaurants;
import in.restroin.restroin.models.MessageModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private List<HangoutRestaurants> hangoutRestaurants;

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String filter_type = getIntent().getStringExtra("filter_type");
        String filter_id = getIntent().getStringExtra("filter_id");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        if(getIntent().getStringExtra("filter_type") == null) {
            RestroINAuthClient authClient = retrofit.create(RestroINAuthClient.class);
            Call<List<HangoutRestaurants>> restaurantsCall = authClient.getRestaurants("", "");
            restaurantsCall.enqueue(new Callback<List<HangoutRestaurants>>() {
                @Override
                public void onResponse(Call<List<HangoutRestaurants>> call, Response<List<HangoutRestaurants>> response) {
                    hangoutRestaurants = response.body();
                    SearchRecyclerAdapter searchRecyclerAdapter = new SearchRecyclerAdapter(hangoutRestaurants);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(searchRecyclerAdapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<List<HangoutRestaurants>> call, Throwable t) {
                    Toast.makeText(SearchActivity.this, "Something Went Wrong. Check you internet connection", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else{
            RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
            Call<List<HangoutRestaurants>> restaurantsCall = inAuthClient.getRestaurants(filter_id, filter_type);
            restaurantsCall.enqueue(new Callback<List<HangoutRestaurants>>() {
                @Override
                public void onResponse(Call<List<HangoutRestaurants>> call, Response<List<HangoutRestaurants>> response) {
                    hangoutRestaurants = response.body();
                    SearchRecyclerAdapter searchRecyclerAdapter = new SearchRecyclerAdapter(hangoutRestaurants);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(searchRecyclerAdapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    final GestureDetector gestureDetector = new GestureDetector(SearchActivity.this, new GestureDetector.OnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return false;
                        }

                        @Override
                        public void onShowPress(MotionEvent e) {

                        }

                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            return false;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {

                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            return false;
                        }
                    });
                    recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                            final View offerView = rv.findChildViewUnder(e.getX(), e.getY());
                            final int position = rv.getChildAdapterPosition(offerView);
                            if(offerView != null && gestureDetector.onTouchEvent(e)){
                                offerView.setScaleX((float)0.985);
                                offerView.setScaleY((float) 0.985);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        offerView.setScaleX((float)1.0);
                                        offerView.setScaleY((float)1.0);
                                    }
                                }, 100);
                                Intent intent = new Intent(SearchActivity.this, RestaurantViewActivity.class);
                                intent.putExtra("restaurant_id", hangoutRestaurants.get(position).getRestaurant_id());
                                startActivity(intent);
                            }
                            return false;
                        }

                        @Override
                        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                        }

                        @Override
                        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<List<HangoutRestaurants>> call, Throwable t) {
                    Toast.makeText(SearchActivity.this, "Something Went Wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

}
