package in.restroin.restroin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import in.restroin.restroin.fragments.BookingStepOneFragment;

import in.restroin.restroin.R;

public class BookingStepAdapter extends AbstractFragmentStepAdapter {
    public BookingStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        return new BookingStepOneFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        return new StepViewModel.Builder(context).setTitle("Details").setBackButtonLabel("Cancel").setEndButtonLabel("Book Now").setBackButtonStartDrawableResId(R.drawable.ic_back_navigte).setNextButtonEndDrawableResId(R.drawable.ic_next_navigation).create();
    }

}
