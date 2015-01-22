package com.traviswooten.playtime.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;
import com.traviswooten.playtime.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser == null) {
            showLoginActivity();
        } else {
            showMainActivity();
        }
    }

    private void showLoginActivity() {
        Intent loginIntent = new Intent(this, SignUpActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void showMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


}
