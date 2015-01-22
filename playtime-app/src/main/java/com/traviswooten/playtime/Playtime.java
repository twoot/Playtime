package com.traviswooten.playtime;

import android.app.Application;

import com.parse.Parse;
import com.parse.PushService;
import com.traviswooten.playtime.activities.MainActivity;

/**
 * Created by traviswooten on 1/15/15.
 */
public class Playtime extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        PushService.setDefaultPushCallback(this, MainActivity.class);

    }
}
