package com.traviswooten.playtime.models;

import com.parse.ParseObject;

/**
 * Created by traviswooten on 1/21/15.
 */
public class PlayPrepared {

    public static String PLAY_PREPARED = "PlayPrepared";
    public static String REQUEST_ID = "requestId";
    public static String REQUESTER_PREPARED = "requesterPrepared";
    public static String RECEIVER_PREPARED = "receiverPrepared";

    private String requestId;
    private boolean requesterIsPrepared;
    private boolean receiverIsPrepared;


    public PlayPrepared(String requestId) {
        this.requestId = requestId;
        this.requesterIsPrepared = false;
        this.receiverIsPrepared = false;
    }

    public void createAndSave() {
        ParseObject startPlay = new ParseObject(PLAY_PREPARED);
        startPlay.put(REQUEST_ID, requestId);
        startPlay.put(REQUESTER_PREPARED, requesterIsPrepared);
        startPlay.put(RECEIVER_PREPARED, receiverIsPrepared);

        startPlay.saveInBackground();
    }
}
