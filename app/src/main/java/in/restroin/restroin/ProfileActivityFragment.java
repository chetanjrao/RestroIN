package in.restroin.restroin;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import in.restroin.restroin.fragments.ChangePasswordFragment;
import in.restroin.restroin.fragments.EditProfileFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RelativeLayout changePassword = (RelativeLayout) view.findViewById(R.id.changePasswordLayout);
        RelativeLayout logoutLayout = (RelativeLayout) view.findViewById(R.id.SignOutLayout);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new ChangePasswordFragment());
            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new EditProfileFragment());
            }
        });
        return view;
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.fragment_container, fragment).addToBackStack("changedFragment").commit();

    }


}
