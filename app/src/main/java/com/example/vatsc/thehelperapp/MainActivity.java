package com.example.vatsc.thehelperapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import  android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SeekBar timeSeekBar;
    TextView timerTextView;
    boolean buttonIsActive;
    Button controllerButton;
    CountDownTimer countDownTimer;
    public  void resetTimer()
    {
        timerTextView.setText("0.00");
        timeSeekBar.setProgress(00);
        timeSeekBar.setEnabled(true);
        countDownTimer.cancel();
        controllerButton.setText("GO!");
        buttonIsActive=false;
    }

    public void updateTimer(int secondsLeft)
    {
        int minutes = (int)(secondsLeft/60);
        int seconds  =secondsLeft-minutes*60;

        String secondString =Integer.toString(seconds);
        if(seconds<=9)
        {
            secondString = "0"+secondString;
        }

        timerTextView.setText(String.valueOf(minutes)+":"+secondString);//show timer in textview
    }

    public void controlTimer(View view)
    {  if(buttonIsActive==false) //to disable the seek bar
        {

        buttonIsActive=true;
        timeSeekBar.setEnabled(false);
        controllerButton.setText("stop");
        countDownTimer= new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000)//100 ms for 0:00
        {

            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);//time decreasing

            }

            @Override
            public void onFinish() {
                timerTextView.setText("0:00");
                MediaPlayer mp3 = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                mp3.start();
                resetTimer();//default values reset
            }
        }.start();
        }
    else
    {
        resetTimer();

    }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeekBar=(SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        controllerButton = (Button)findViewById(R.id.controllerButton);

        timeSeekBar.setMax(1800);//10 minutes maximum
        timeSeekBar.setProgress(00);//default showing

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
