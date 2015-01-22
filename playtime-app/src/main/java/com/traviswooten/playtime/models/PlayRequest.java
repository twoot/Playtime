package com.traviswooten.playtime.models;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by traviswooten on 1/21/15.
 */
public class PlayRequest {

    public static String PLAY_REQUEST = "PlayRequest";
    private String REQUEST_USER = "requestUser";
    private String RECEIVE_USER = "receiveUser";
    private String URL = "url";
    public static String ACCEPTED = "accepted";


    private String requestUser, receiveUser;
    private String url;
    private boolean accepted;
    private ParseObject playRequest;

    public PlayRequest(String user1, String user2, String url, boolean accepted) {
        this.url = url;
        this.requestUser = user1;
        this.receiveUser = user2;
        this.accepted = accepted;
    }

    public PlayRequest(String user2, String url) {
        this.requestUser = ParseUser.getCurrentUser().getUsername();
        this.receiveUser = user2;
        this.url = url;
        this.accepted = false;
    }

    public void createAndSave() {
        playRequest = new ParseObject(PLAY_REQUEST);
        playRequest.put(REQUEST_USER, requestUser);
        playRequest.put(RECEIVE_USER, receiveUser);
        playRequest.put(URL, url);
        playRequest.put(ACCEPTED, accepted);

        try {
            playRequest.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        try {
            ParseObject serverRequest = playRequest.fetch();
            return serverRequest.getObjectId();
        } catch (ParseException e) {
            return null;
        }
    }

    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
