package in.restroin.restroin.fragments;

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
import android.widget.Toast;

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

public class ChangePasswordFragment extends Fragment {

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    public ChangePasswordFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_change_password, container, false);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        final TextInputEditText old_password = (TextInputEditText) view.findViewById(R.id.old_password);
        final TextInputEditText new_password = (TextInputEditText) view.findViewById(R.id.new_password);
        final TextInputEditText confirm_password = (TextInputEditText) view.findViewById(R.id.confirm_password);
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        ImageButton accept = (ImageButton) view.findViewById(R.id.accept_button);
        ImageButton close = (ImageButton) view.findViewById(R.id.exit_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!TextUtils.isEmpty(new_password.getText().toString()) && !TextUtils.isEmpty(old_password.getText().toString()) && !TextUtils.isEmpty(confirm_password.getText().toString())){
                    if(new_password.getText().toString().equals(confirm_password.getText().toString())){
                        progressBar.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.GONE);
                        RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
                        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                        Call<MessageModel> messageModelCall = inAuthClient.changePassword("Bearer " + saveSharedPreferences.getAccess_token(v.getContext()), old_password.getText().toString(), new_password.getText().toString());
                        messageModelCall.enqueue(new Callback<MessageModel>() {
                            @Override
                            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                                if(Integer.parseInt(response.body().getStatus()) == 200){
                                    Toast.makeText(v.getContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    relativeLayout.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(v.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    relativeLayout.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageModel> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Something went wrong. Check your internet Connection", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        new_password.setError("Passwords Don't Match");
                        confirm_password.setError("Passwords Don't Match");
                    }
                } else {
                    if(TextUtils.isEmpty(old_password.getText().toString())){
                        old_password.setError("Enter your old password");
                    } else {
                        if(TextUtils.isEmpty(new_password.getText().toString())){
                            new_password.setError("Enter your new password");
                        } else  {
                            if(TextUtils.isEmpty(confirm_password.getText().toString())){
                                confirm_password.setError("Enter your new password");
                            }
                        }
                    }
                }
            }
        });
        return view;
    }


}
