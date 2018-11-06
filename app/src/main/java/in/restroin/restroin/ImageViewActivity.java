package in.restroin.restroin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import in.restroin.restroin.adapters.ImagesAdapter;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.ImageModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageViewActivity extends AppCompatActivity {

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        String id = getIntent().getStringExtra("id");
        RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
        Call<ImageModel> imageModelCall = inAuthClient.getImages(id);
        ImageButton exit = (ImageButton) findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageModelCall.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                if(response.body() != null){
                    ImagesAdapter imagesAdapter = new ImagesAdapter(response.body().getImages());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(imagesAdapter);
                } else {
                    Toast.makeText(ImageViewActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(ImageViewActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
