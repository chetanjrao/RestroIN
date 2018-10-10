package in.restroin.restroin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton exit_button = (ImageButton) findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("isBackStack") == null){
                    Toast.makeText(LoginActivity.this, "HI", Toast.LENGTH_SHORT).show();
                    Intent goToUserFeed = new Intent(LoginActivity.this, UserFeedActivity.class);
                    startActivity(goToUserFeed);
                } else {
                    finish();
                }
            }
        });
    }
}
