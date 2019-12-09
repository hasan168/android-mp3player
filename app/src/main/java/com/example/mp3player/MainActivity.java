package com.example.mp3player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView musicImage;
    TextView musicName;
    SeekBar timeZone;
    Button btnPlay,btnPause;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicImage = findViewById(R.id.imageView);
        musicName =  findViewById(R.id.textView);
        timeZone = findViewById(R.id.seekBar);
        btnPlay = findViewById(R.id.button3);
        btnPause = findViewById(R.id.button4);
        handler = new Handler();

        musicImage.setImageResource(R.drawable.batman);
        musicName.setText("Hans Zimmer - A Watchful Guardian The Dark Knight");

        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);

    }
    Integer pauseLength = 0 ;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button3:
                if ( mediaPlayer != null&&mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    pauseLength = mediaPlayer.getCurrentPosition();
                    btnPlay.setBackgroundResource(R.drawable.play);
                    break;
                }
                else {
                    if (musicName.getText() == "Hans Zimmer - Time Inception") {
                        mediaPlayer = MediaPlayer.create(this, R.raw.time);
                    }
                    else{
                        mediaPlayer = MediaPlayer.create(this, R.raw.awatchfulguardian);
                    }
                    timeZone.setMax(mediaPlayer.getDuration());
                    if (!pauseLength.equals(0)){
                        mediaPlayer.seekTo(pauseLength);
                    }
                    mediaPlayer.start();
                    changeSeekbar();
                    btnPlay.setBackgroundResource(R.drawable.pause);
                    timeZone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            if(b){
                                mediaPlayer.seekTo(i);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    break;
                }
            case R.id.button4:
                if (!mediaPlayer.isPlaying()) {
                    btnPlay.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                    changeSeekbar();
                }
                mediaPlayer.seekTo(0);
                break;
        }
    }

    public void changeSeekbar(){
        timeZone.setProgress(mediaPlayer.getCurrentPosition());
        if(mediaPlayer.isPlaying()){
            runnable =  new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            handler.postDelayed(runnable,500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.musics,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            timeZone.setMax(mediaPlayer.getDuration());
            timeZone.setProgress(0);
            mediaPlayer.seekTo(0);
        }
        pauseLength = 0;
        switch (id){
            case R.id.msc1:
                musicImage.setImageResource(R.drawable.batman);
                musicName.setText("Hans Zimmer - A Watchful Guardian The Dark Knight");
                break;
            case R.id.msc2:
                musicImage.setImageResource(R.drawable.inception);
                musicName.setText("Hans Zimmer - Time Inception");
        }
        btnPlay.setBackgroundResource(R.drawable.play);
        //timeZone.setMax(mediaPlayer.getDuration());
        return super.onOptionsItemSelected(item);
    }
}
