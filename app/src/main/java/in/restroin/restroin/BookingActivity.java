package in.restroin.restroin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingActivity extends AppCompatActivity{

    List<String> dates = new ArrayList<>();
    List<String> people = new ArrayList<>();
    List<String> timeList = new ArrayList<>();
    String restaurant_id, user_id, visiting_date, visiting_time, number_of_male, guest_name, guest_email, guest_phone, access_token;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://www.restroin.in/developers/api/restroin/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    public List<String> getTimeList() throws ParseException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(calendar.getTime());
        String closingTimeDate = getIntent().getStringExtra("restaurant_closing_time");
        while(dateFormat.parse(currentTime).before(dateFormat.parse(closingTimeDate))){
            timeList.add(dateFormat.format(toNearestWholeHour(dateFormat.parse(currentTime))));
            calendar.setTime(dateFormat.parse(currentTime));
            calendar.add(Calendar.MINUTE, 30);
            currentTime = dateFormat.format(calendar.getTime());
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
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        dates.add("Today");
        dates.add("Tomorrow");
        for(int i=2; i <10; i++){
            int new_date = calendar.get(Calendar.DATE) + i;
            dates.add(new_date + "-" + decideMonthName(calendar.get(Calendar.MONTH)));
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
        RecyclerView dates_recycler = (RecyclerView) findViewById(R.id.dates_recycler);
        RecyclerView people_recycler_view = (RecyclerView) findViewById(R.id.people_recycler);
        RecyclerView times_recycler = (RecyclerView) findViewById(R.id.time_recycler);
        LinearLayoutManager timeLinearLayoutManager = new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        TimeChooseAdapter timeChooseAdapter = null;
        try {
            timeChooseAdapter = new TimeChooseAdapter(getTimeList());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        times_recycler.setLayoutManager(timeLinearLayoutManager);
        times_recycler.setAdapter(timeChooseAdapter);
        for(int i=1; i <= 25; i++){
            people.add(""+i);
        }
        PeopleChooseAdapter peopleChooseAdapter = new PeopleChooseAdapter(people);
        LinearLayoutManager peopleLayoutManager = new  LinearLayoutManager(BookingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        people_recycler_view.setLayoutManager(peopleLayoutManager);
        people_recycler_view.setAdapter(peopleChooseAdapter);
        DatesChooseAdapter adapter = new DatesChooseAdapter(getListOfDates());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        dates_recycler.setLayoutManager(linearLayoutManager);
        dates_recycler.setAdapter(adapter);
        addBooking();
        Button book_now_button = (Button) findViewById(R.id.book_now_button);
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
        DatesChooseAdapter adapter = new DatesChooseAdapter(getListOfDates());
        RestroINAuthClient authClient = retrofit.create(RestroINAuthClient.class);
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        this.restaurant_id = getIntent().getStringExtra("restaurant_id");
        Toast.makeText(this, "date: " + adapter.getSelectedDate(), Toast.LENGTH_SHORT).show();
    }
}
