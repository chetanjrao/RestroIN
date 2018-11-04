package in.restroin.restroin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.LoginModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import in.restroin.restroin.utils.ServiceGenerator;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkAccessTokenStatus();
        final ImageButton exit_button = (ImageButton) findViewById(R.id.exit_button);
        final RelativeLayout progressLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
        final RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        main_layout.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.forgot_password);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        final TextInputEditText username_editText = (TextInputEditText) findViewById(R.id.login_username);
        final TextInputEditText password_editText = (TextInputEditText) findViewById(R.id.login_password);
        Button loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_editText.getText().toString();
                String password = password_editText.getText().toString();
                if(!TextUtils.isEmpty(username)){
                    if(!TextUtils.isEmpty(password)){
                        if(isEmailValid(username)){
                            final String device_uid = FirebaseInstanceId.getInstance().getToken();
                            main_layout.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.VISIBLE);
                            exit_button.setVisibility(View.GONE);
                            validateUser(username, password, device_uid);
                        } else {
                            username_editText.setError("Kindly enter a valid email address");
                        }
                    } else {
                        password_editText.setError("Kindly enter the password");
                    }
                } else {
                    username_editText.setError("Kindly enter a valid email address");
                }
            }
        });
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("isBackStack") == null){
                    Intent goToUserFeed = new Intent(LoginActivity.this, UserFeedActivity.class);
                    startActivity(goToUserFeed);
                } else {
                    finish();
                }
            }
        });
        final Button signUpButton = (Button) findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignup = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToSignup);
            }
        });
    }


    public void validateUser(final String username, String password,final String device_uid){
        RestroINAuthClient authClient = new ServiceGenerator().createService(RestroINAuthClient.class, username, password);
        final RelativeLayout progressLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
        final RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        Call<LoginModel> modelCall = authClient.authenticateUser(device_uid);
        modelCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                String statusCode = response.body().getStatus();
                if(statusCode != null && Integer.parseInt(response.body().getStatus()) == 200){
                    String access_token = response.body().getToken();
                    String user_id = response.body().getId();
                    String mobile_no = response.body().getMobile_number();
                    String image = response.body().getImage();
                    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                    saveSharedPreferences.setSharedPreferences(LoginActivity.this, username, access_token, mobile_no, user_id, device_uid, image);
                    Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, UserFeedActivity.class);
                    startActivity(intent);
                } else if(Integer.parseInt(response.body().getStatus()) == 402) {
                    Toast.makeText(LoginActivity.this, "A confirmation email has been sent to you email address", Toast.LENGTH_SHORT).show();
                    final ImageButton exit_button = (ImageButton) findViewById(R.id.exit_button);
                    final RelativeLayout progressLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
                    final RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_layout);
                    main_layout.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);
                    exit_button.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid Login Credentials ", Toast.LENGTH_SHORT).show();
                    final ImageButton exit_button = (ImageButton) findViewById(R.id.exit_button);
                    final RelativeLayout progressLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
                    final RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_layout);
                    main_layout.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);
                    exit_button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                final ImageButton exit_button = (ImageButton) findViewById(R.id.exit_button);
                Toast.makeText(LoginActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                progressLayout.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                exit_button.setVisibility(View.VISIBLE);
            }
        });

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void checkAccessTokenStatus(){
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        String access_token = saveSharedPreferences.getAccess_token(LoginActivity.this);
        if(access_token != null){
            Intent goToLogin = new Intent(LoginActivity.this, UserFeedActivity.class);
            goToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToLogin);
        }
    }
}
