package in.restroin.restroin.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.restroin.restroin.R;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileFragment extends Fragment{

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    private Uri uri;

    public EditProfileFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_edit_profile, container, false);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        final TextInputEditText profile_first_name = (TextInputEditText) view.findViewById(R.id.profile_first_name);
        final TextInputEditText profile_last_name = (TextInputEditText) view.findViewById(R.id.profile_last_name);
        final TextInputEditText profile_email_edit = (TextInputEditText) view.findViewById(R.id.profile_email);
        profile_first_name.setText(new SaveSharedPreferences().getFirst_name(view.getContext()));
        profile_last_name.setText(new SaveSharedPreferences().getLast_name(view.getContext()));
        profile_email_edit.setText(new SaveSharedPreferences().getEmail(view.getContext()));
        ImageButton ic_done = (ImageButton) view.findViewById(R.id.accept_button);
        ImageButton ic_exit = (ImageButton) view.findViewById(R.id.exit_button);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ic_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!TextUtils.isEmpty(profile_email_edit.getText().toString())){
                    if(!TextUtils.isEmpty(profile_first_name.getText().toString())){
                        if(isEmailValid(profile_email_edit.getText().toString())){
                            progressBar.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.GONE);
                            final String first_name = profile_first_name.getText().toString();
                            final String last_name = profile_last_name.getText().toString();
                            final String email = profile_email_edit.getText().toString();
                            RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
                            final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                            Call<MessageModel> modelCall = inAuthClient.updateProfile("Bearer " + saveSharedPreferences.getAccess_token(v.getContext()), "EditBasic", first_name, last_name, email);
                            modelCall.enqueue(new Callback<MessageModel>() {
                                @Override
                                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                                    if(Integer.parseInt(response.body().getStatus()) == 200){
                                        progressBar.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        Toast.makeText(v.getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                        saveSharedPreferences.setEmail(email, v.getContext());
                                        saveSharedPreferences.setFirst_name(first_name, v.getContext());
                                        saveSharedPreferences.setLast_name(last_name, v.getContext());
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        Toast.makeText(v.getContext(), "Something Went Wrong. Try again", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageModel> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    Toast.makeText(v.getContext(), "Something Went Wrong. Check your internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            profile_email_edit.setError("Enter a valid email address");
                        }
                    } else {
                        profile_first_name.setError("Enter you name");
                    }
                } else {
                    profile_email_edit.setError("Enter your email");
                }
            }
        });
        return view;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
