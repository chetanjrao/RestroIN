package in.restroin.restroin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/hobo.ttf");
        TextView textView = (TextView) findViewById(R.id.restroin);
        textView.setTypeface(typeface);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this,UserFeedActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

}
}
