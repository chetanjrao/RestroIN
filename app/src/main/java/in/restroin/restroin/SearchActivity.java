package in.restroin.restroin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import in.restroin.restroin.adapters.SearchRecyclerAdapter;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.HangoutRestaurants;
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
        getAllRestaurants();
    }

    public void getAllRestaurants(){
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        RestroINAuthClient authClient = retrofit.create(RestroINAuthClient.class);
        Call<List<HangoutRestaurants>> restaurantsCall = authClient.getRestaurants("");
        restaurantsCall.enqueue(new Callback<List<HangoutRestaurants>>() {
            @Override
            public void onResponse(Call<List<HangoutRestaurants>> call, Response<List<HangoutRestaurants>> response) {
                hangoutRestaurants = response.body();
                SearchRecyclerAdapter searchRecyclerAdapter = new SearchRecyclerAdapter(hangoutRestaurants);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(searchRecyclerAdapter);

            }

            @Override
            public void onFailure(Call<List<HangoutRestaurants>> call, Throwable t) {

            }
        });
    }
}
