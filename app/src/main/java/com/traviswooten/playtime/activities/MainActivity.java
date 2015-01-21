package com.traviswooten.playtime.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.traviswooten.playtime.R;

import java.io.IOException;

public class MainActivity extends Activity {
    private Button btn;
    private ProgressDialog progress;
    private String TAG = "MainActivity";
    /**
     * help to toggle between play and pause.
     */
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;
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
        progress = new ProgressDialog(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPrepared = true;
                progress.cancel();
                mediaPlayer.start();
            }
        });
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        btn.setPressed(true);
                        handleSoundPlay();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        btn.setPressed(false);
                        handleSoundPlay();
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
        Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void handleSoundPlay(){
        if (!playPause) {
            btn.setBackgroundResource(R.drawable.ic_action_pause);
            try {
                if (!mediaPlayer.isPlaying() && isPrepared) {
                    mediaPlayer.start();
                } else {
                    progress.setMessage("Buffering...");
                    progress.show();
                    mediaPlayer.setDataSource(URL);
                    mediaPlayer.prepareAsync();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            playPause = true;
        } else {
            btn.setBackgroundResource(R.drawable.ic_action_play);
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            playPause = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}