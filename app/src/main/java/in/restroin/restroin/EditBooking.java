package in.restroin.restroin;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.BookingStepModel;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditBooking extends AppCompatActivity {

    private String restaurant_phone;
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
        setContentView(R.layout.activity_edit_booking);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
        ImageButton imageButton = (ImageButton) findViewById(R.id.exit_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final TextInputEditText guest_name = (TextInputEditText) findViewById(R.id.guest_name);
        final TextInputEditText guest_email = (TextInputEditText) findViewById(R.id.guest_email);
        final TextInputEditText restaurant_name = (TextInputEditText) findViewById(R.id.restaurant_name);
        final TextInputEditText total_guests = (TextInputEditText) findViewById(R.id.total_no_of_guests);
        final TextInputEditText arrival = (TextInputEditText) findViewById(R.id.visiting_time);
        final TextInputEditText couponSelected = (TextInputEditText) findViewById(R.id.couponSelected);
        final RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
        final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        Call<BookingStepModel> modelCall = inAuthClient.getStepBookingData("Bearer " + saveSharedPreferences.getAccess_token(EditBooking.this), getIntent().getStringExtra("booking_id"));
        modelCall.enqueue(new Callback<BookingStepModel>() {
            @Override
            public void onResponse(Call<BookingStepModel> call, Response<BookingStepModel> response) {
                    guest_name.setText(response.body().getName());
                    guest_email.setText(response.body().getEmail());
                    restaurant_name.setText(response.body().getRestaurant_name());
                    total_guests.setText(response.body().getGuest());
                    arrival.setText(response.body().getTime());
                    couponSelected.setText(response.body().getCoupon());
                    restaurant_phone = response.body().getPhone();
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<BookingStepModel> call, Throwable t) {

            }
        });
        Button button = (Button) findViewById(R.id.cancelButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
                Call<MessageModel> canCelCall = inAuthClient.cancelBooking("Bearer " + saveSharedPreferences.getAccess_token(v.getContext()), "Cancel", getIntent().getStringExtra("booking_id"), restaurant_name.getText().toString(), guest_name.getText().toString(), saveSharedPreferences.getMobile_no(v.getContext()), restaurant_phone);
                canCelCall.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if(Integer.parseInt(response.body().getStatus()) == 200){
                            Toast.makeText(EditBooking.this, "Your booking has been canceled", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditBooking.this, ProfileActivity.class);
                            finish();
                        } else {
                            Toast.makeText(EditBooking.this, "Something went wrong. Try again " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        Toast.makeText(EditBooking.this, "Something went wrong. Try again " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
