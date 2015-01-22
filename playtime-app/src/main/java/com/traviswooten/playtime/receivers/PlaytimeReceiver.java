package com.traviswooten.playtime.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.traviswooten.playtime.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaytimeReceiver extends BroadcastReceiver {
    public PlaytimeReceiver() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String jsonString = intent.getStringExtra(Constants.DATA);
        JSONObject data = null;
        if(jsonString != null) {
            try {
                data = new JSONObject(jsonString);
                handleJSONData(context, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void handleJSONData(Context context, JSONObject data) throws JSONException {
        //OVERRIDE
    }
}
