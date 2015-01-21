package com.traviswooten.playtime.models;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by traviswooten on 1/21/15.
 */
public class PlayRequest {

    private String PLAY_REQUEST = "PlayRequest";
    private String USER1 = "user1";
    private String USER2 = "user2";
    private String URL = "url";
    private String ACCEPTED = "accepted";


    private String user1, user2;
    private String url;
    private boolean accepted;

    public PlayRequest(String user1, String user2, String url, boolean accepted) {
        this.url = url;
        this.user1 = user1;
        this.user2 = user2;
        this.accepted = accepted;
    }

    public PlayRequest(String user2, String url) {
        this.user1 = ParseUser.getCurrentUser().getUsername();
        this.user2 = user2;
        this.url = url;
        this.accepted = false;
    }

    public void createAndSave() {
        ParseObject playRequest = new ParseObject(PLAY_REQUEST);
        playRequest.add(USER1, user1);
        playRequest.add(USER2, user2);
        playRequest.add(URL, url);
        playRequest.add(ACCEPTED, accepted);

        playRequest.saveInBackground();
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
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
