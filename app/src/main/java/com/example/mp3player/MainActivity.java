package com.example.mp3player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button3:
                if ( mediaPlayer != null&&mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
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

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            timeZone.setMax(mediaPlayer.getDuration());
                            mediaPlayer.start();
                            changeSeekbar();
                        }
                    });
                    //mediaPlayer.start();
                    //timeZone.setMax(mediaPlayer.getDuration());
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
                mediaPlayer.reset();
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
        switch (String.valueOf(item.getTitle())){
            case "music1":
                musicImage.setImageResource(R.drawable.batman);
                musicName.setText("Hans Zimmer - A Watchful Guardian The Dark Knight");
                break;
            case "music2":
                musicImage.setImageResource(R.drawable.inception);
                musicName.setText("Hans Zimmer - Time Inception");
        }
        return super.onOptionsItemSelected(item);
    }
}
