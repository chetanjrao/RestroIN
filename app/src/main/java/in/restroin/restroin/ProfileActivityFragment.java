package in.restroin.restroin;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.restroin.restroin.adapters.ProfileBookingStatusAdapter;
import in.restroin.restroin.fragments.ChangePasswordFragment;
import in.restroin.restroin.fragments.EditProfileFragment;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.BookingStatusModel;
import in.restroin.restroin.models.DiningModel;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment implements EasyPermissions.PermissionCallbacks{
    ArrayList<ArrayList<BookingStatusModel>> allBookings;
    ArrayList<BookingStatusModel> ActiveBookings, PendingBookings;
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    private Uri uri;

    public ProfileActivityFragment() {
        checkAccessTokenStatus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkAccessTokenStatus();
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RelativeLayout changePassword = (RelativeLayout) view.findViewById(R.id.changePasswordLayout);
        RelativeLayout editProfileLayout = (RelativeLayout) view.findViewById(R.id.EditProfileLayout);
        RelativeLayout logoutLayout = (RelativeLayout) view.findViewById(R.id.SignOutLayout);
        ImageButton exit = (ImageButton) view.findViewById(R.id.exit_button);
        RelativeLayout feebackLayout = (RelativeLayout) view.findViewById(R.id.feedBackLayout);
        feebackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("support@restroin.in") +
                        "?subject=" + Uri.encode("RestroIN Service Feedback") +
                        "&body=" + Uri.encode("");
                Uri uri = Uri.parse(uriText);
                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send Feedback"));
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dining_status_recycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
        final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        Call<DiningModel> diningModelCall = inAuthClient.getDiningHistory("Bearer " + saveSharedPreferences.getAccess_token(view.getContext()), saveSharedPreferences.getUser_id(view.getContext()));
        diningModelCall.enqueue(new Callback<DiningModel>() {
            @Override
            public void onResponse(Call<DiningModel> call, final Response<DiningModel> response) {
                ProfileBookingStatusAdapter profileBookingStatusAdapter = new ProfileBookingStatusAdapter(response.body().getBookings());
                dine_count.setText(response.body().getDine_count());
                profile_name.setText(response.body().getName());
                Uri uri = Uri.parse(saveSharedPreferences.getImage(getContext()));
                Picasso.get().load(uri).into(profile_image);
                if(saveSharedPreferences.getFirst_name(view.getContext()) == null || saveSharedPreferences.getFirst_name(view.getContext()).equals("")){
                    saveSharedPreferences.setFirst_name(response.body().getFirst_name(), view.getContext());
                }
                if(saveSharedPreferences.getLast_name(view.getContext()) == null || saveSharedPreferences.getLast_name(view.getContext()).equals("")){
                    saveSharedPreferences.setLast_name(response.body().getLast_name(), view.getContext());
                }
                profile_email.setText(saveSharedPreferences.getEmail(view.getContext()));
                profile_number.setText(saveSharedPreferences.getMobile_no(view.getContext()));
                Uri image = Uri.parse(saveSharedPreferences.getImage(view.getContext()));
                Picasso.get().load(image).into(profile_image);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(profileBookingStatusAdapter);
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                final GestureDetector gestureDetector = new GestureDetector(view.getContext(), new GestureDetector.OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return false;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {

                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {

                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        return false;
                    }
                });
                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        final View offerView = rv.findChildViewUnder(e.getX(), e.getY());
                        final int position = rv.getChildAdapterPosition(offerView);
                        if(offerView != null && gestureDetector.onTouchEvent(e)){
                            offerView.setScaleX((float)0.985);
                            offerView.setScaleY((float) 0.985);
                            Intent intent = new Intent(view.getContext(), EditBooking.class);
                            intent.putExtra("booking_id", response.body().getBookings().get(position).getBooking_id());
                            startActivity(intent);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    offerView.setScaleX((float)1.0);
                                    offerView.setScaleY((float)1.0);
                                }
                            }, 100);
                        }
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                });
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





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1 && data != null && data.getData() != null) {
            uri = data.getData();
//            try{
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
//                Toast.makeText(getContext(), "source: " + imageUri, Toast.LENGTH_SHORT).show();
//                CircleImageView circleImageView = (CircleImageView) getView().findViewById(R.id.profile_image);
//                circleImageView.setImageBitmap(bitmap);
//            } catch (IOException e){
//                e.printStackTrace();
//            }
            if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                final ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.ProgressBar);
                final RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.main_layout);
                final CircleImageView circleImageView = (CircleImageView) getView().findViewById(R.id.profile_image);
                final Uri[] image_path = new Uri[1];
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
                String file_path = getRealPathFromUriPath(uri, getActivity());
                File file = new File(file_path);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
                RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "EditImage");
                RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
                final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                Call<MessageModel> messageModelCall = inAuthClient.updateImage("Bearer " + saveSharedPreferences.getAccess_token(getContext()), fileToUpload, fileName, action);
                messageModelCall.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if(Integer.parseInt(response.body().getStatus()) == 200){
                            saveSharedPreferences.setImage("https://www.restroin.in/developers/api/images/" + response.body().getMessage(), getContext());
                            Toast.makeText(getContext(), "Profile Image Updated", Toast.LENGTH_SHORT).show();
                            image_path[0] = Uri.parse(saveSharedPreferences.getImage(getContext()));
                            Picasso.get().load(image_path[0]).into(circleImageView);
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getContext(), "Something Went Wrong " + response.message(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Something Went Wrong " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                EasyPermissions.requestPermissions(getContext(), "RestroIN needs to access your files", 300, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }


    public String getRealPathFromUriPath(Uri contenUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(contenUri, null, null, null, null);
        if(cursor == null){
            return contenUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return  cursor.getString(index);
        }


    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (uri != null) {
            final ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.ProgressBar);
            final RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.main_layout);
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            final CircleImageView circleImageView = (CircleImageView) getView().findViewById(R.id.profile_image);
            final Uri[] image_path = new Uri[1];
            String file_path = getRealPathFromUriPath(uri, getActivity());
            File file = new File(file_path);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
            RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "EditProfile");
            RestroINAuthClient inAuthClient = retrofit.create(RestroINAuthClient.class);
            final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
            Call<MessageModel> messageModelCall = inAuthClient.updateImage("Bearer " + saveSharedPreferences.getAccess_token(getContext()), fileToUpload, fileName, action);
            messageModelCall.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    if (Integer.parseInt(response.body().getStatus()) == 200) {
                        saveSharedPreferences.setImage("https://www.restroin.in/developers/api/images/" + response.body().getMessage(), getContext());
                        Toast.makeText(getContext(), "Profile Image Updated", Toast.LENGTH_SHORT).show();
                        image_path[0] = Uri.parse(saveSharedPreferences.getImage(getContext()));
                        Picasso.get().load(image_path[0]).into(circleImageView);
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(getContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
    }

    public void checkAccessTokenStatus(){
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        String access_token = saveSharedPreferences.getAccess_token(getContext());
        if(access_token == null){
            Intent goToLogin = new Intent(getContext(), LoginActivity.class);
            startActivity(goToLogin);
        }
    }
}
