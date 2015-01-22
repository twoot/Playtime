package com.traviswooten.playtime.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PlaytimeReceiver extends BroadcastReceiver {
    public PlaytimeReceiver() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String jsonString = intent.getStringExtra("data");
        JSONObject data = null;
        if(jsonString != null) {
            try {
                data = new JSONObject(jsonString);
                if(data.getString("action").equals("play-request")) {
                    String requestId = data.getString("requestId");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayRequest");
                    query.whereEqualTo("objectId", requestId);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if(list.size()>0) {
                                ParseObject playRequest = list.get(0);
                                playRequest.put("accepted", true);
                                playRequest.saveInBackground();
//                                Toast.makeText(context, playRequest.getString("url"), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else if (data.getString("action").equals("play-request-accepted")) {
                    Intent updateButton = new Intent();
                    updateButton.putExtra("requestId", data.getString("requestId"));
                    updateButton.setAction(data.getString("action"));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(updateButton);
                } else if (data.getString("action").equals("start-play")) {
                    Intent updateButton = new Intent();
                    updateButton.putExtra("url", data.getString("url"));
                    updateButton.setAction(data.getString("action"));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(updateButton);
                    System.out.println("Start play! " + ParseUser.getCurrentUser().getUsername() + " " + data.getString("requestId"));
                } else if (data.getString("action").equals("play-prepared")) {
                    Intent updateButton = new Intent();
                    updateButton.setAction(data.getString("action"));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(updateButton);
                    System.out.println("Play prepared! " + ParseUser.getCurrentUser().getUsername() + " " + data.getString("requestId"));
                } else {
                    Toast.makeText(context, data.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
