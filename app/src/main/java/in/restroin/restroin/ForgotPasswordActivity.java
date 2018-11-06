package in.restroin.restroin;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.MessageModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_forgot_password);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        final RelativeLayout progressBarLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
        final TextInputEditText email = (TextInputEditText) findViewById(R.id.reset_email);
        relativeLayout.setVisibility(View.VISIBLE);
        ImageButton imageButton = (ImageButton) findViewById(R.id.exit_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBarLayout.setVisibility(View.GONE);
        Button button = (Button) findViewById(R.id.reset_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email.getText().toString()) && isEmailValid(email.getText().toString())){
                    relativeLayout.setVisibility(View.GONE);
                    progressBarLayout.setVisibility(View.VISIBLE);
                    RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
                    Call<MessageModel> messageModelCall = inAuthClient.resetPassword(email.getText().toString());
                    messageModelCall.enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            if(Integer.parseInt(response.body().getStatus()) == 200){
                                Toast.makeText(ForgotPasswordActivity.this, "Reset link sent to you email address", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if(Integer.parseInt(response.body().getStatus()) == 202){
                                Toast.makeText(ForgotPasswordActivity.this, "Reset link sent to you email address", Toast.LENGTH_SHORT).show();
                                finish();

                            } else if(Integer.parseInt(response.body().getStatus()) == 203){
                                Toast.makeText(ForgotPasswordActivity.this, "No user found with your email address", Toast.LENGTH_SHORT).show();
                                relativeLayout.setVisibility(View.VISIBLE);
                                progressBarLayout.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                relativeLayout.setVisibility(View.VISIBLE);
                                progressBarLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {
                            Toast.makeText(ForgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            relativeLayout.setVisibility(View.VISIBLE);
                            progressBarLayout.setVisibility(View.GONE);
                        }
                    });
                } else {
                    if(!TextUtils.isEmpty(email.getText().toString())){
                        if(!isEmailValid(email.getText().toString())){
                            email.setError("Enter valid email address");
                        }
                    } else {
                        email.setError("Enter your email address");
                    }
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
