package com.zampegab.lavozregional_fm909;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

import com.spoledge.aacdecoder.AACPlayer;

import com.spoledge.aacdecoder.AACPlayer;
import com.spoledge.aacdecoder.PlayerCallback;
import android.media.AudioTrack;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    //private MediaPlayer player;
    private AACPlayer player;
    private String url;
    private ImageButton buttonPlay;
    private ImageButton buttonMute;
    private ImageView buttonFacebook;
    private ImageView buttonTwitter;
    private ImageView buttonInstagram;
    private ImageView buttonWhtasapp;
    private boolean playing;
    private boolean muted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = getString(R.string.app_url);
        buttonPlay = findViewById(R.id.btn_play_stop);
        buttonPlay.setOnClickListener(this);
        buttonMute = findViewById(R.id.btn_mute);
        buttonMute.setOnClickListener(this);
        buttonFacebook = findViewById(R.id.facebook);
        buttonFacebook.setOnClickListener(this);
        buttonTwitter = findViewById(R.id.twitter);
        buttonTwitter.setOnClickListener(this);
        buttonInstagram = findViewById(R.id.instagram);
        buttonInstagram.setOnClickListener(this);
        buttonWhtasapp = findViewById(R.id.whatsapp);
        buttonWhtasapp.setOnClickListener(this);
        playing = false;
        muted = false;
        // Inicializo el objeto AACPlayer
        initalizeAACPlayer();


    }

    public void onClick(View v) {
        if (v == buttonPlay) {
            if (!playing) {
                startPlaying();
            } else {
                stopPlaying();
            }
        } else if (v == buttonMute) {
            if (!muted) {
                //mutate();
                muted = true;
            } else {
                //commute();
                muted = false;
            }
        } else if (v == buttonFacebook) {
            goToSocialNetwork(getString(R.string.url_face));
        } else if (v == buttonTwitter) {
            goToSocialNetwork(getString(R.string.url_tw));
        } else if (v == buttonInstagram) {
            goToSocialNetwork(getString(R.string.url_insta));
        } else if (v == buttonWhtasapp) {
            goToSocialNetwork(getString(R.string.url_wsapp)+getString(R.string.numero_wsapp));
        }
    }

    private void initalizeAACPlayer(){
        this.player = new AACPlayer(new PlayerCallback() {
            public void playerStarted() {
                MainActivity.this.playing = true;
            }

            public void playerPCMFeedBuffer(boolean isPlaying, int bufSizeMs, int bufCapacityMs) {
                MainActivity.this.playing = isPlaying;
            }

            public void playerStopped(int perf) {
                MainActivity.this.playing = false;
            }

            public void playerException(Throwable t) {
                MainActivity.this.playing = false;
            }

            public void playerMetadata(String key, String value) {
            }

            public void playerAudioTrackCreated(AudioTrack arg0) {
            }
        });
    }
    public void startPlaying() {
        try {
            buttonPlay.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
            player.playAsync(this.url,128);
        } catch (IllegalArgumentException | SecurityException | IllegalStateException  e) {
            Toast.makeText(getApplicationContext(),"Error al conectar con la radio", Toast.LENGTH_LONG).show();
        }
    }

    public void stopPlaying() {
        player.stop();
        buttonPlay.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
    }
/*
    public void mutate() {
        player.setVolume(0, 0);
        buttonMute.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
    }

    public void commute() {
        player.setVolume(1, 1);
        buttonMute.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
    }
 */

    public void goToSocialNetwork(String url) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browse);
    }
}
