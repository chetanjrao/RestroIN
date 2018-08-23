package in.restroin.restroin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import in.restroin.restroin.adapters.BookingStepAdapter;

public class BookingActivity extends AppCompatActivity implements StepperLayout.StepperListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        StepperLayout layout = (StepperLayout) findViewById(R.id.booking_steppers);
        BookingStepAdapter adapter = new BookingStepAdapter(getSupportFragmentManager(), BookingActivity.this);
        layout.setAdapter(adapter);
        layout.setListener(BookingActivity.this);

    }

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }
}
