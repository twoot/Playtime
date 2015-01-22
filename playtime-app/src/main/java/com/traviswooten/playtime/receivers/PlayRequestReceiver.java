package com.traviswooten.playtime.receivers;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.traviswooten.playtime.constants.Constants;
import com.traviswooten.playtime.models.PlayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PlayRequestReceiver extends PlaytimeReceiver {
    public PlayRequestReceiver() {
    }

    @Override
    protected void handleJSONData(Context context, JSONObject data) throws JSONException {
        if(data.getString(Constants.ACTION).equals(Constants.PLAY_REQUEST)) {
            String requestId = data.getString(Constants.REQUEST_ID);

            ParseQuery<ParseObject> query = ParseQuery.getQuery(PlayRequest.PLAY_REQUEST);
            query.whereEqualTo(Constants.OBJECT_ID, requestId);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if(list.size()>0) {
                        ParseObject playRequest = list.get(0);
                        playRequest.put(PlayRequest.ACCEPTED, true);
                        playRequest.saveInBackground();
                    }
                }
            });
        }
    }
}
