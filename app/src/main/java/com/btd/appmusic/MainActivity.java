package com.btd.appmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.seekBar=(SeekBar) findViewById(R.id.sound_bar);
       // this.mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.my_sound);
        String url="http://mediaserv30.live-streams.nl:8086/live";
        this.mediaPlayer=new MediaPlayer();

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);
            }
        },1000,1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(seekBar.getProgress()*1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void playSound(View view) {
        Button button=(Button) view;
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            button.setText(getResources().getString(R.string.play_music_btn));
        }
        else {

            mediaPlayer.start();
            button.setText(getResources().getString(R.string.pause_music_btn));
        }
    }
}