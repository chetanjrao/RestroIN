package in.restroin.restroin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import in.restroin.restroin.fragments.BookingStepOneFragment;
import in.restroin.restroin.fragments.BookingStepThreeFragment;
import in.restroin.restroin.fragments.BookingStepTwoFragment;

public class BookingStepAdapter extends AbstractFragmentStepAdapter {
    public BookingStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Step returnStep = null;
        switch (position){
            case 0:
                final BookingStepOneFragment bookingStepOneFragment = new BookingStepOneFragment();
                returnStep = bookingStepOneFragment;
                break;
            case 1:
                final BookingStepTwoFragment bookingStepTwoFragment = new BookingStepTwoFragment();
                returnStep = bookingStepTwoFragment;
                break;
            case 2:
                final BookingStepThreeFragment bookingStepThreeFragment = new BookingStepThreeFragment();
                returnStep = bookingStepThreeFragment;
                break;
        }
        return returnStep;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        switch (position){
            case 0:
                return new StepViewModel.Builder(context).setTitle("One step").create();
            case 1:
                return new StepViewModel.Builder(context).setTitle("Two step").create();
            case 2:
                return new StepViewModel.Builder(context).setTitle("Three step").create();
        }
        return null;
    }
}
