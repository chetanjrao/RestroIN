package in.restroin.restroin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.restroin.restroin.adapters.ProfileBookingStatusAdapter;
import in.restroin.restroin.fragments.ChangePasswordFragment;
import in.restroin.restroin.fragments.EditProfileFragment;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.BookingStatusModel;
import in.restroin.restroin.models.DiningModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {
    ArrayList<ArrayList<BookingStatusModel>> allBookings;
    ArrayList<BookingStatusModel> ActiveBookings, PendingBookings;
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RelativeLayout changePassword = (RelativeLayout) view.findViewById(R.id.changePasswordLayout);
        RelativeLayout editProfileLayout = (RelativeLayout) view.findViewById(R.id.EditProfileLayout);
        RelativeLayout logoutLayout = (RelativeLayout) view.findViewById(R.id.SignOutLayout);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new ChangePasswordFragment());
            }
        });
        editProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new EditProfileFragment());
            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Are you sure to log out ?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                        saveSharedPreferences.removeSharedPrefernces(v.getContext());
                        Intent goToLogin = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(goToLogin);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        final TextView dine_count = (TextView) view.findViewById(R.id.total_dines);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
        final TextView profile_name = (TextView) view.findViewById(R.id.profile_name);
        final TextView profile_email = (TextView) view.findViewById(R.id.profile_email);
        final TextView profile_number = (TextView) view.findViewById(R.id.profile_number);
        final CircleImageView profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dining_status_recycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
        final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        Call<DiningModel> diningModelCall = inAuthClient.getDiningHistory("Bearer " + saveSharedPreferences.getAccess_token(view.getContext()), saveSharedPreferences.getUser_id(view.getContext()));
        diningModelCall.enqueue(new Callback<DiningModel>() {
            @Override
            public void onResponse(Call<DiningModel> call, Response<DiningModel> response) {
                ProfileBookingStatusAdapter profileBookingStatusAdapter = new ProfileBookingStatusAdapter(response.body().getBookings());
                dine_count.setText(response.body().getDine_count());
                profile_name.setText(response.body().getName());
                profile_email.setText(saveSharedPreferences.getEmail(view.getContext()));
                profile_number.setText(saveSharedPreferences.getMobile_no(view.getContext()));
                Uri image = Uri.parse(saveSharedPreferences.getImage(view.getContext()));
                Picasso.get().load(image).into(profile_image);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(profileBookingStatusAdapter);
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<DiningModel> call, Throwable t) {
                Toast.makeText(view.getContext(), "Something went Wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        return view;
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragment).addToBackStack("changedFragment").commit();

    }


}
