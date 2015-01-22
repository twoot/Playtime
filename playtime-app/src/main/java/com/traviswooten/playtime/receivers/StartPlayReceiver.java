package com.traviswooten.playtime.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.traviswooten.playtime.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class StartPlayReceiver extends PlaytimeReceiver {
    public StartPlayReceiver() {
    }

    @Override
    protected void handleJSONData(Context context, JSONObject data) throws JSONException {
        if (data.getString(Constants.ACTION).equals(Constants.START_PLAY)) {
            Intent updateButton = new Intent();
            updateButton.putExtra(Constants.URL, data.getString(Constants.URL));
            updateButton.setAction(data.getString(Constants.ACTION));
            LocalBroadcastManager.getInstance(context).sendBroadcast(updateButton);
        }
    }
}
