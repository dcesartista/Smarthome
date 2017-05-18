package id.ac.ugm.smartcity.smarthome.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);
        Intent i;
        if( preferences.getString(App.ACCESS_TOKEN,"").equals("")){
            i = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            i = new Intent(SplashActivity.this, DashBoardActivity.class);
        }

        startActivity(i);
        finish();
    }
}
