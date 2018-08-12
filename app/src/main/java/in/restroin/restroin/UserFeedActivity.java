package in.restroin.restroin;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import in.restroin.restroin.adapters.OffersAdapter;
import in.restroin.restroin.interfaces.OfferClient;
import in.restroin.restroin.models.Offers;
import in.restroin.restroin.utils.OfferDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFeedActivity extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://restroin.in/")
            .addConverterFactory(GsonConverterFactory.create());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ShowOffers(UserFeedActivity.this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void ShowOffers(final Context context){
        Retrofit retrofit_offers = builder.build();
        final RecyclerView offersRecycler = (RecyclerView) findViewById(R.id.offers_recycler_view);
        final LinearLayoutManager OffersLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        offersRecycler.setLayoutManager(OffersLayoutManager);
        OfferClient offerClient = retrofit_offers.create(OfferClient.class);
        Call<List<Offers>> offersCall = offerClient.getOffers();
        offersCall.enqueue(new Callback<List<in.restroin.restroin.models.Offers>>() {
            @Override
            public void onResponse(Call<List<Offers>> call, Response<List<Offers>> response) {
                if(response.isSuccessful()){
                    List<Offers> offers = response.body();
                    OffersAdapter offersAdapter = new OffersAdapter(offers, context);
                    offersRecycler.setAdapter(offersAdapter);
                    Toast.makeText(context, "Retrived URI : " + response.body().get(0).getImage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Mesage: " + response.message() , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something Went wrong : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Offers>> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
