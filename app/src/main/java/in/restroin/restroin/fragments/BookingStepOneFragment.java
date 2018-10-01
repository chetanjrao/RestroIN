package in.restroin.restroin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import in.restroin.restroin.R;
import in.restroin.restroin.adapters.DatesChooseAdapter;
import in.restroin.restroin.adapters.PeopleChooseAdapter;
import in.restroin.restroin.utils.SmoothCheckBox;

public class BookingStepOneFragment extends Fragment implements BlockingStep {
    List<String> dates = new ArrayList<>();
    List<String> people = new ArrayList<>();

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View StepOneView = inflater.inflate(R.layout.fragment_booking_step_one, container, false);
        RecyclerView dates_recycler = (RecyclerView) StepOneView.findViewById(R.id.dates_recycler);
        RecyclerView people_recycler_view = (RecyclerView) StepOneView.findViewById(R.id.people_recycler);
        for(int i=1; i < 10; i++){
            people.add(""+i);
        }
        people.add("10+");
        PeopleChooseAdapter peopleChooseAdapter = new PeopleChooseAdapter(people);
        LinearLayoutManager peopleLayoutManager = new  LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false);
        people_recycler_view.setLayoutManager(peopleLayoutManager);
        people_recycler_view.setAdapter(peopleChooseAdapter);
        DatesChooseAdapter adapter = new DatesChooseAdapter(getListOfDates());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false);
        dates_recycler.setLayoutManager(linearLayoutManager);
        dates_recycler.setAdapter(adapter);
        return StepOneView;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.goToNextStep();
            }
        }, 0L);
    }

    @Override
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Your booking is done", Toast.LENGTH_SHORT).show();
            }
        }, 0L);
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

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
}
