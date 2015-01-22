package com.traviswooten.playtime.models;

import com.parse.ParseObject;

/**
 * Created by traviswooten on 1/21/15.
 */
public class StartPlay {

    public static String START_PLAY = "StartPlay";
    public static String REQUEST_ID = "requestId";
    public static String REQUESTER_PUSHED = "requesterPushed";
    public static String RECEIVER_PUSHED = "receiverPushed";

    private String requestId;
    private boolean requesterIsPushed;
    private boolean receiverIsPushed;


    public StartPlay(String requestId) {
        this.requestId = requestId;
        this.requesterIsPushed = false;
        this.receiverIsPushed = false;
    }

    public void createAndSave() {
        ParseObject startPlay = new ParseObject(START_PLAY);
        startPlay.put(REQUEST_ID, requestId);
        startPlay.put(REQUESTER_PUSHED, requesterIsPushed);
        startPlay.put(RECEIVER_PUSHED, receiverIsPushed);

        startPlay.saveInBackground();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isRequesterIsPushed() {
        return requesterIsPushed;
    }

    public void setRequesterIsPushed(boolean isPushed) {
        this.requesterIsPushed = isPushed;
    }
}
