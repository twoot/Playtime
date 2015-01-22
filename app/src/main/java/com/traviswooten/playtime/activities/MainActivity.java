package com.traviswooten.playtime.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.traviswooten.playtime.R;
import com.traviswooten.playtime.models.PlayPrepared;
import com.traviswooten.playtime.models.PlayRequest;
import com.traviswooten.playtime.models.StartPlay;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    private Button btn, requestButton;
    private EditText username;
    private TextView accepted;
    private ProgressDialog progress;
    private String TAG = "MainActivity";
    /**
     * help to toggle between play and pause.
     */
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;
    private boolean isRequester = false;
    private String requestId;
    /**
     * remain false till media is not completed, inside OnCompletionListener make it true.
     */
    private boolean intialStage = true;
    private String URL = "https://dl.dropboxusercontent.com/u/4009537/Layer%20Down%20ft.%20Cameron%20Bedell.mp3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button1);
        username = (EditText) findViewById(R.id.username);
        accepted = (TextView) findViewById(R.id.request_accepted);
        requestButton = (Button) findViewById(R.id.request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPlayRequest();
            }
        });

        progress = new ProgressDialog(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPrepared = true;
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(PlayPrepared.PLAY_PREPARED);
                query.whereEqualTo(PlayPrepared.REQUEST_ID, requestId);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        ParseObject playPrepared = parseObjects.get(0);
                        if(isRequester) {
                            playPrepared.put(PlayPrepared.REQUESTER_PREPARED, true);
                        } else {
                            playPrepared.put(PlayPrepared.RECEIVER_PREPARED, true);
                        }
                        playPrepared.saveInBackground();
                    }
                });
            }
        });


        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        btn.setPressed(true);
                        handlePressHold();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        btn.setPressed(false);
                        handlePressRelease();
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }

                return true;
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", currentUser.getUsername());
        installation.saveInBackground();

        Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_LONG).show();

    }

    public void handlePressHold() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(StartPlay.START_PLAY);
        query.whereEqualTo(StartPlay.REQUEST_ID, requestId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ParseObject startPlay = parseObjects.get(0);
                if(isRequester) {
                    startPlay.put(StartPlay.REQUESTER_PUSHED, true);
                } else {
                    startPlay.put(StartPlay.RECEIVER_PUSHED, true);
                }
                startPlay.saveInBackground();
            }
        });

    }

    public void handlePressRelease() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(StartPlay.START_PLAY);
        query.whereEqualTo(StartPlay.REQUEST_ID, requestId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ParseObject startPlay = parseObjects.get(0);
                if(isRequester) {
                    startPlay.put(StartPlay.REQUESTER_PUSHED, false);
                } else {
                    startPlay.put(StartPlay.RECEIVER_PUSHED, false);
                }
                startPlay.saveInBackground();
            }
        });

    }

    public void sendPlayRequest() {
        isRequester = true;
        String usernameParam = username.getText().toString();
        PlayRequest playRequest = new PlayRequest(usernameParam, URL);
        playRequest.createAndSave();
        String playRequestId = playRequest.getId();

        StartPlay startPlay = new StartPlay(playRequestId);
        startPlay.createAndSave();
        PlayPrepared playPrepared = new PlayPrepared(playRequestId);
        playPrepared.createAndSave();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", usernameParam);
        params.put("requestUsername", ParseUser.getCurrentUser().getUsername());
        params.put("requestId", playRequestId);

        ParseCloud.callFunctionInBackground("sendPlaytimeRequest", params, new FunctionCallback<String>() {
            public void done(String message, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Push error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void handleSoundPrepare(String url){
        try {
            btn.setBackgroundColor(getResources().getColor(R.color.blue));
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSoundPlay() {
        if (!mediaPlayer.isPlaying() && isPrepared) {
            mediaPlayer.start();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(playRequestAcceptedReceiver, new IntentFilter("play-request-accepted"));
        LocalBroadcastManager.getInstance(this).registerReceiver(startPlayReceiver, new IntentFilter("start-play"));
        LocalBroadcastManager.getInstance(this).registerReceiver(playPreparedReceiver, new IntentFilter("play-prepared"));
    }

    // handler for received Intents for the "my-event" event
    private BroadcastReceiver playRequestAcceptedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            requestId = intent.getStringExtra("requestId");
            btn.setBackgroundColor(getResources().getColor(R.color.orange));
        }
    };

    private BroadcastReceiver startPlayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btn.setBackgroundColor(getResources().getColor(R.color.yellow));
            handleSoundPrepare(intent.getStringExtra("url"));
        }
    };

    private BroadcastReceiver playPreparedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btn.setBackgroundColor(getResources().getColor(R.color.green));
            handleSoundPlay();
        }
    };

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playRequestAcceptedReceiver);
        super.onPause();
    }

}