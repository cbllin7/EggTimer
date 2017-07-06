package com.kingofaustell.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //create variable for seekbar
    SeekBar timerControl;

    //create variable for text view
    TextView timer;

    //create variable for button
    Button stopButton;

    //create variable for CountDownTimer method
    CountDownTimer countDownTimer;

    //create boolean to manage rather or not the timer is active
    Boolean active = false;

    //create a method to update the time
    public void updateTimer(int secondLeft){
        //uses the variable secondLeft to get minutes and seconds
        int minutes = (int)secondLeft/60;
        int seconds = secondLeft - minutes * 60;

        //change the text view to show the time
        timer.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));

    }

    //create a method to reset the timer
    public void resetTimer(){

        timer.setText("0:30");
        timerControl.setProgress(30);
        countDownTimer.cancel();
        stopButton.setText("Start");
        timerControl.setEnabled(true);
        active = false;

    }

    //create method that controls the time and the progress of the timer
    public void controlTime(View view) {

        if (active == false) {

            //if button is pressed while active is "false" then it sets active to true, disable seekbar, and change button text to stop
            active = true;
            timerControl.setEnabled(false);
            stopButton.setText("Stop");

            //set variable countDownTimer to new CountDownTimer which starts the timer and counts down by every second from
            //info from seek bar.......CountDownTimer is in millisecond so one second equals 1000
            countDownTimer = new CountDownTimer(timerControl.getProgress() * 1000 + 100, 1000) {

                //onTick method is created to make changes while timer is going
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                //onFinish method is created to make changes as the timer finished
                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), );
                    mediaPlayer.start();
                }
            }.start();

        }else{
            //if button is pressed while "active" is true
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.Timer);

        stopButton = (Button) findViewById(R.id.TimeButton);

        timerControl = (SeekBar) findViewById(R.id.TimeBar);
        timerControl.setMax(600);
        timerControl.setProgress(30);

        final Handler handler = new Handler();

        final Runnable run = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, timerControl.getProgress());
            }
        };

        timerControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                handler.post(run);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}