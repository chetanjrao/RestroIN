package in.restroin.restroin;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.LoginModel;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnTouchListener{

    private PopupWindow popupWindow;

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
        setContentView(R.layout.activity_register);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        ImageButton exit = (ImageButton) findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final TextInputEditText name = (TextInputEditText) findViewById(R.id.register_name);
        final TextInputEditText email = (TextInputEditText) findViewById(R.id.register_username);
        final TextInputEditText number = (TextInputEditText) findViewById(R.id.register_mobile);
        final TextInputEditText password = (TextInputEditText) findViewById(R.id.register_password);
        final Button button = (Button) findViewById(R.id.register_button);
        final View popupContent = getLayoutInflater().inflate(R.layout.layout_otp_popup, null);
        popupWindow = new PopupWindow();
        popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popupContent);
        popupContent.findViewById(R.id.popup_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(number.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && isEmailValid(email.getText().toString()) && number.getText().toString().length() == 10){
                    progressBar.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    final RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
                    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                    Call<MessageModel> otpCall = inAuthClient.otpGenerator("Bearer Open", "generateOtp", number.getText().toString());
                    otpCall.enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            if(Integer.parseInt(response.body().getStatus()) == 200){
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                                Toast.makeText(RegisterActivity.this, "OTP is valid for 10 mins", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                                Toast.makeText(RegisterActivity.this, "Something Went Wrong. Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, "Something Went Wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    if(!TextUtils.isEmpty(name.getText().toString())){
                        if(!TextUtils.isEmpty(email.getText().toString())){
                            if(isEmailValid(email.getText().toString())){
                                if(!TextUtils.isEmpty(number.getText().toString())){
                                    if(number.getText().toString().length() == 10){
                                        if(TextUtils.isEmpty(password.getText().toString())){
                                            password.setError("Enter your password");
                                        }
                                    } else {
                                        number.setError("Enter valid mobile number");
                                    }
                                } else {
                                    number.setError("Enter your mobile number");
                                }
                            } else {
                                email.setError("Enter valid email address");
                            }
                        } else {
                            email.setError("Enter your email address");
                        }
                    } else {
                        name.setError("Enter your name");
                    }
                }
            }
        });

        popupContent.findViewById(R.id.otpProgressBar).setVisibility(View.GONE);
        popupContent.findViewById(R.id.otp_layout_complete).setVisibility(View.VISIBLE);
        popupContent.findViewById(R.id.otp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupContent.findViewById(R.id.otpProgressBar).setVisibility(View.VISIBLE);
                popupContent.findViewById(R.id.otp_layout_complete).setVisibility(View.GONE);
                TextInputEditText otp = (TextInputEditText) popupContent.findViewById(R.id.otp_edit_text);
                final RestroINAuthClient restroINAuthClient = retrofit.create(RestroINAuthClient.class);
                Call<MessageModel> loginModelCall = restroINAuthClient.registerUser(name.getText().toString(), email.getText().toString(), password.getText().toString(), FirebaseInstanceId.getInstance().getToken(), number.getText().toString(), "AddUser");
                loginModelCall.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if(Integer.parseInt(response.body().getStatus()) == 200){
                            Toast.makeText(RegisterActivity.this, "Thank you for registering", Toast.LENGTH_SHORT).show();
                            Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(goToLogin);
                        }else if(Integer.parseInt(response.body().getStatus()) == 203){
                            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Something Went Wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        popupWindow.setTouchInterceptor(this);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
