package in.restroin.restroin;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import in.restroin.restroin.adapters.BookingStepAdapter;
import in.restroin.restroin.adapters.DatesChooseAdapter;
import in.restroin.restroin.adapters.PeopleChooseAdapter;
import in.restroin.restroin.adapters.TimeChooseAdapter;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.utils.SaveSharedPreferences;
import in.restroin.restroin.utils.SmoothCheckBox;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import in.restroin.restroin.interfaces.onAdapterItemCheckListener;

public class BookingActivity extends AppCompatActivity implements onAdapterItemCheckListener, View.OnTouchListener {

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    private PopupWindow popupWindow;
    List<String> dates = new ArrayList<>();
    List<String> people = new ArrayList<>();
    List<String> timeList = new ArrayList<>();
    private String selectedDate;
    private String selectedTime, selectedPeople;
    String restaurant_id, user_id, visiting_date, visiting_time, number_of_male, guest_name, guest_email, guest_phone, access_token, couponSelected;

    Retrofit.Builder builder = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    public List<String> getTimeList() throws ParseException {
        if(timeList.size() == 0) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            String currentTime = dateFormat.format(calendar.getTime());
            String closingTimeDate = getIntent().getStringExtra("restaurant_closing_time");
            String openTimeDate = getIntent().getStringExtra("restaurant_opening_time");
            while (dateFormat.parse(currentTime).after(dateFormat.parse(openTimeDate)) && dateFormat.parse(currentTime).before(dateFormat.parse(closingTimeDate))) {
                timeList.add(dateFormat.format(toNearestWholeHour(dateFormat.parse(currentTime))));
                calendar.setTime(dateFormat.parse(currentTime));
                calendar.add(Calendar.MINUTE, 30);
                currentTime = dateFormat.format(calendar.getTime());
            }
        }
        return timeList;
    }

    static Date toNearestWholeHour(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTime(d);

        if (c.get(Calendar.MINUTE) < 30) {
            c.set(Calendar.MINUTE, 30);
        } else if (c.get(Calendar.MINUTE) >= 30){
            c.add(Calendar.HOUR, 1);
            c.set(Calendar.MINUTE, 0);
        }

        return c.getTime();
    }

    public List<String> getListOfDates(){
        if(dates.size() == 0) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            dates.add("Today");
            dates.add("Tomorrow");
            for (int i = 2; i < 10; i++) {
                int new_date = calendar.get(Calendar.DATE) + i;
                int new_month = calendar.get(Calendar.MONTH) + 1;
                if (new_month == 1 || new_month == 3 || new_month == 5 || new_month == 7 || new_month == 8 || new_month == 10 || new_month == 12) {
                    if (new_date > 31) {
                        new_date = new_date - 31;
                        new_month = new_month + 1;
                    }
                } else if (new_month == 2 || new_month == 4 || new_month == 6 || new_month == 9 || new_month == 11) {
                    if (new_date > 30) {
                        new_date = new_date - 30;
                        new_month = new_month + 1;
                    }
                }
                dates.add(new_date + "-" + new_month);
            }
        }
        return dates;
    }

    private String decideMonthName(int month){
        String month_name = null;
        switch (month+1){
            case 1:
                month_name = "Jan";
                break;
            case 2:
                month_name = "Feb";
                break;
            case 3:
                month_name = "Mar";
                break;
            case 4:
                month_name = "Apr";
                break;
            case 5:
                month_name = "May";
                break;
            case 6:
                month_name = "Jun";
                break;
            case 7:
                month_name = "Jul";
                break;
            case 8:
                month_name = "Aug";
                break;
            case 9:
                month_name = "Sep";
                break;
            case 10:
                month_name = "Oct";
                break;
            case 11:
                month_name = "Nov";
                break;
            case 12:
                month_name = "Dec";
                break;
        }
        return month_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        checkAccessTokenStatus();
        final RecyclerView dates_recycler = (RecyclerView) findViewById(R.id.dates_recycler);
        RecyclerView people_recycler_view = (RecyclerView) findViewById(R.id.people_recycler);
        RecyclerView times_recycler = (RecyclerView) findViewById(R.id.time_recycler);
        final TextInputEditText guest_name_edit_text = (TextInputEditText) findViewById(R.id.guest_name);
        guest_name_edit_text.setText("");
        final TextInputEditText guest_email_edit_text = (TextInputEditText) findViewById(R.id.guest_email);
        final TextInputEditText guest_phone_edit_text = (TextInputEditText) findViewById(R.id.guest_number);
        final SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        if(saveSharedPreferences.getEmail(BookingActivity.this) != null){
            guest_email_edit_text.setText(saveSharedPreferences.getEmail(BookingActivity.this));
        } else {
            guest_email_edit_text.setEnabled(true);
            guest_email_edit_text.setClickable(true);
            guest_email_edit_text.setFocusable(true);
        }
        if(saveSharedPreferences.getMobile_no(BookingActivity.this) != null){
            guest_phone_edit_text.setText(saveSharedPreferences.getMobile_no(BookingActivity.this));
        }
        LinearLayoutManager timeLinearLayoutManager = new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        times_recycler.setLayoutManager(timeLinearLayoutManager);
        try {
            timeList = getTimeList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimeChooseAdapter timeChooseAdapter = new TimeChooseAdapter(timeList);
        times_recycler.setAdapter(timeChooseAdapter);
        for(int i=1; i <= 25; i++){
            people.add(""+i);
        }
        final PeopleChooseAdapter peopleChooseAdapter = new PeopleChooseAdapter(people);
        LinearLayoutManager peopleLayoutManager = new  LinearLayoutManager(BookingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        people_recycler_view.setLayoutManager(peopleLayoutManager);
        people_recycler_view.setAdapter(peopleChooseAdapter);
        final DatesChooseAdapter adapter = new DatesChooseAdapter(getListOfDates());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        dates_recycler.setLayoutManager(linearLayoutManager);
        dates_recycler.setAdapter(adapter);
        addBooking();
        Button book_now_button = (Button) findViewById(R.id.book_now_button);
        final TimeChooseAdapter finalTimeChooseAdapter = timeChooseAdapter;
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
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
        book_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                selectedDate = getListOfDates().get(adapter.getSelectedDate());
                switch (selectedDate){
                    case "Today":
                        selectedDate = calendar.get(Calendar.DATE) + "-" + (calendar.get(Calendar.MONTH) + 1);
                        break;
                    case "Tomorrow":
                        selectedDate = (calendar.get(Calendar.DATE) + 1) + "-" + (calendar.get(Calendar.MONTH) + 1);
                }try {
                    selectedTime = getTimeList().get(finalTimeChooseAdapter.getSelectedDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                selectedPeople = people.get(peopleChooseAdapter.getPeopleCount());
                if(TextUtils.isEmpty(guest_name_edit_text.getText().toString())){
                    guest_name_edit_text.setError("Enter the Guest Name");
                } else {
                    restaurant_id = getIntent().getStringExtra("restaurant_id");
                    user_id = saveSharedPreferences.getUser_id(BookingActivity.this);
                    visiting_date = dates.get(adapter.getSelectedDate());
                    visiting_time = timeList.get(finalTimeChooseAdapter.getSelectedDate());
                    number_of_male = people.get(peopleChooseAdapter.getPeopleCount());
                    guest_name = guest_name_edit_text.getText().toString();
                    guest_email = guest_email_edit_text.getText().toString();
                    guest_phone = guest_phone_edit_text.getText().toString();
                    couponSelected = getIntent().getStringExtra("couponSelected");
                    RestroINAuthClient authClient = retrofit.create(RestroINAuthClient.class);
                    Call<MessageModel> otpCall = authClient.otpGenerator("Bearer " + saveSharedPreferences.getAccess_token(BookingActivity.this), "generateOtp", guest_phone_edit_text.getText().toString());
                    otpCall.enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            if(Integer.parseInt(response.body().getStatus()) == 200){
                                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                                Toast.makeText(BookingActivity.this, "OTP is valid for 10 mins", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BookingActivity.this, "Something Went Wrong. Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {
                            Toast.makeText(BookingActivity.this, "Something Went Wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                Call<MessageModel> otpVerifier = restroINAuthClient.otpVerifier("Bearer " + new SaveSharedPreferences().getAccess_token(BookingActivity.this), "validateOtp", guest_phone_edit_text.getText().toString(), otp.getText().toString());
                otpVerifier.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if(Integer.parseInt(response.body().getStatus()) == 200){
                            Call<MessageModel> bookingCall = restroINAuthClient.ManageReservation("Bearer " + saveSharedPreferences.getAccess_token(BookingActivity.this), "AddBooking", restaurant_id, user_id, visiting_date, visiting_time, number_of_male, guest_name, guest_phone, guest_email, couponSelected);
                            bookingCall.enqueue(new Callback<MessageModel>() {
                                @Override
                                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                                    if(Integer.parseInt(response.body().getStatus()) == Integer.parseInt("200")){
                                        Toast.makeText(BookingActivity.this, "Your Booking has been successfully placed", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageModel> call, Throwable t) {
                                    Toast.makeText(BookingActivity.this, "Something Went Wrong. Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                    popupContent.findViewById(R.id.otpProgressBar).setVisibility(View.GONE);
                                    popupContent.findViewById(R.id.otp_layout_complete).setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                    }
                });
            }
        });
        popupWindow.setTouchInterceptor(this);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


    @Override
    protected void onStart() {
        checkAccessTokenStatus();
        super.onStart();
    }

    public void checkAccessTokenStatus(){
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        String access_token = saveSharedPreferences.getAccess_token(BookingActivity.this);
        if(access_token == null){
            Intent goToLogin = new Intent(BookingActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        }
    }

    public void addBooking(){
        RestroINAuthClient authClient = retrofit.create(RestroINAuthClient.class);
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        this.restaurant_id = getIntent().getStringExtra("restaurant_id");
    }

    @Override
    public void onItemCheck(String selectedData) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
