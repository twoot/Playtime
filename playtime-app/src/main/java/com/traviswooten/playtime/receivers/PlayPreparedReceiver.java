package com.traviswooten.playtime.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.traviswooten.playtime.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayPreparedReceiver extends PlaytimeReceiver {
    public PlayPreparedReceiver() {
    }

    @Override
    protected void handleJSONData(Context context, JSONObject data) throws JSONException {
        if (data.getString(Constants.ACTION).equals(Constants.PLAY_PREPARED)) {
            Intent updateButton = new Intent();
            updateButton.setAction(data.getString(Constants.ACTION));
            LocalBroadcastManager.getInstance(context).sendBroadcast(updateButton);
        }
    }
}
