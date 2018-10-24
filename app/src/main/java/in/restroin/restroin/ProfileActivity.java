package in.restroin.restroin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import in.restroin.restroin.utils.SaveSharedPreferences;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        checkAccessTokenStatus();
        ProfileActivityFragment profileActivityFragment = new ProfileActivityFragment();
        addFragment(profileActivityFragment);

    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.add(R.id.fragment_container, fragment).commit();

    }

    @Override
    public void onBackPressed() {
        android.app.FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            //addFragment(new ProfileActivityFragment());
        } else {
            super.onBackPressed();
        }
    }
    public void checkAccessTokenStatus(){
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        String access_token = saveSharedPreferences.getAccess_token(ProfileActivity.this);
        if(access_token == null){
            Intent goToLogin = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        }
    }
}
