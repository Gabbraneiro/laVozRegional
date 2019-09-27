package com.example.lavozregional_fm909;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import android.os.Bundle;
import android.view.View;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private MediaPlayer player;
    private String url;
    private Button buttonPlay;
    private boolean playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = getString(R.string.app_url);
        buttonPlay = findViewById(R.id.play);
        buttonPlay.setOnClickListener(this);
        playing = false;
        // Inicializo el objeto MediaPlayer
        initializeMediaPlayer();
    }

    public void onClick(View v) {
        if (v == buttonPlay) {
            if (!playing) {
                startPlaying();
                playing = true;

            } else {
                stopPlaying();
                playing = false;

            }
        }
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i("Buffering", "" + percent);
            }
        });
    }

    public void startPlaying() {
        try {
            //player.reset();
            player.setDataSource(url);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    buttonPlay.setText(R.string.radio_stop);
                }
            });
            player.prepareAsync();


        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Error al conectar con la radio", Toast.LENGTH_LONG).show();
        }
    }

    public void stopPlaying(){
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }
        buttonPlay.setText(R.string.radio_play);
    }
}
