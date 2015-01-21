package com.traviswooten.playtime.models;

import com.parse.ParseObject;

/**
 * Created by traviswooten on 1/21/15.
 */
public class StartPlayAck {

    private String START_PLAY = "StartPlayAck";
    private String REQUEST_ID = "requestId";
    private String PUSHED = "isPushed";

    private String requestId;
    private boolean isPushed;


    public StartPlayAck(String requestId, boolean isPushed) {
        this.requestId = requestId;
        this.isPushed = isPushed;
    }

    public void createAndSave() {
        ParseObject startPlay = new ParseObject(START_PLAY);
        startPlay.add(REQUEST_ID, requestId);
        startPlay.add(PUSHED, isPushed);

        startPlay.saveInBackground();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isPushed() {
        return isPushed;
    }

    public void setPushed(boolean isPushed) {
        this.isPushed = isPushed;
    }
}
