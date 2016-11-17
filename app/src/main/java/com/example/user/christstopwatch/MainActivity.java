package com.example.user.christstopwatch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button left,right;
    private TextView clock;
    private boolean isRunning;
    private int counter;
    private Timer timer;
    private ClockTask clockTask;
    private UIHandler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        uiHandler = new UIHandler();
        timer = new Timer();
        left = (Button)findViewById(R.id.btnleft);
        right = (Button)findViewById(R.id.btnright);
        clock = (TextView)findViewById(R.id.timer);
        //lapTime = (TextView)findViewById(R.id.lapTime);

    }

    @Override
    public void finish() {
        if(timer!=null){
            timer.purge();
            timer.cancel();
            timer = null;
        }
        super.finish();
    }

    public void doLeft(View view){
        if(isRunning){
            doLap();
        }else{
            doReset();
        }
    }
    public void doRight(View view){
        isRunning = !isRunning;
        right.setText(isRunning?"stop":"start");
        left.setText(isRunning?"lap":"restart");
        if(isRunning){
            doStart();
        }else{
            doStop();
        }
    }
    private void doStart(){
        clockTask = new ClockTask();
        timer.schedule(clockTask,10,10);
    }
    private void doStop(){
        if (clockTask !=null){
            clockTask.cancel();
        }
    }
    private void doLap(){

    }
    private void doReset(){
        counter=0;
        uiHandler.sendEmptyMessage(0);
    }

    private class ClockTask extends TimerTask{
        @Override
        public void run() {
            counter++;
            uiHandler.sendEmptyMessage(0);
        }
    }
    private class UIHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clock.setText("" + counter);
        }
    }
    static String counter2Clock(int counter){
        return "00:01:02.12";
    }

}
