package com.traviswooten.playtime.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaytimeReceiver extends BroadcastReceiver {
    public PlaytimeReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        JSONObject message = null;
        try {
            message = new JSONObject(data);
            System.out.println("TRAVIS CUSTOM PUSH RECEIVED: " + message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
