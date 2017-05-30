package com.example.dlawogus.taekwongo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_SCROLL;

public class MainActivity extends AppCompatActivity{

    public static TextView blue, red;
    public static int BlueScore, RedScore, tempBlueScore, tempRedScore;
    public static String up, down, left, right;
    public static String signUp, signDown, signLeft, signRight;
    private TextView time;
    private ImageButton start, options, lock;
    private boolean initialStart, started;
    public static long secondsLeft;
    private static Timer T;

    static Bundle data;

    static final String logTag = "ActivitySwipeDetector";
    private Activity activity;
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //default gestures
        up = "3";
        down = "1";
        left = "undo";
        right = "";

        signUp = "+";
        signDown="-";
        signLeft="+";
        signRight="+";

        //Score PIV
        blue = (TextView) findViewById(R.id.scoreBlue);
        red = (TextView) findViewById(R.id.scoreRed);
        BlueScore = RedScore = 0;

        //Start boolean PIV
        initialStart = started = false;

        //ImageButton PIV
        start = (ImageButton) findViewById(R.id.play);
        options = (ImageButton) findViewById(R.id.settings);
        lock = (ImageButton) findViewById(R.id.lock);

        //Timer PIV
        secondsLeft = 180000;
        T = new Timer(secondsLeft, 1000);
        time = (TextView) findViewById(R.id.Timer);

        blue.setText(String.valueOf(BlueScore));
        red.setText(String.valueOf(RedScore));

        //Start/Pause Button listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                started = !started;

                if(started) {
                    initialStart = true;
                    T.Start();
                }
                else {
                    initialStart = false;
                    T.Stop();
                }
            }
        });

        //lock listener
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started)
                    initialStart = !initialStart;
            }
        });

        //Blue/Red Gesture Event Listener
        blue.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            downX = event.getX();
                            downY = event.getY();
                            return true;
                        }
                        case MotionEvent.ACTION_UP: {
                            upX = event.getX();
                            upY = event.getY();

                            float deltaX = downX - upX;
                            float deltaY = downY - upY;

                            // swipe horizontal?
                            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                                if (Math.abs(deltaX) > MIN_DISTANCE) {
                                    // left or right
                                    if (deltaX < 0) {
                                        this.onRightSwipe();
                                        return true;
                                    }
                                    if (deltaX > 0) {
                                        this.onLeftSwipe();
                                        return true;
                                    }
                                } else {
                                    if(initialStart) {
                                        tempBlueScore = BlueScore;
                                        BlueScore++;
                                        blue.setText(String.valueOf(BlueScore));
                                    }
                                }
                            }
                            // swipe vertical?
                            else {
                                if (Math.abs(deltaY) > MIN_DISTANCE) {
                                    // top or down
                                    if (deltaY < 0) {
                                        this.onDownSwipe();
                                        return true;
                                    }
                                    if (deltaY > 0) {
                                        this.onUpSwipe();
                                        return true;
                                    }
                                } else {
                                    if(initialStart) {
                                        tempBlueScore = BlueScore;
                                        BlueScore++;
                                        blue.setText(String.valueOf(BlueScore));
                                    }
                                }
                            }

                            return true;
                        }
                    }
                    return false;
                }

                private void onUpSwipe() {
                    if(initialStart) {
                        if(up.isEmpty())
                            BlueScore = BlueScore;
                        else if(up.equals("undo")) {
                            BlueScore = tempBlueScore;
                        }
                        else if(signUp.equals("+")) {
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore + Integer.valueOf(up);
                        }
                        else if(signUp.equals("-")) {
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore - Integer.valueOf(up);
                        }
                        else
                            BlueScore = BlueScore;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }

                private void onDownSwipe() {
                    if(initialStart) {
                        if(down.isEmpty())
                            BlueScore = BlueScore;
                        else if(down.equals("undo")) {
                            BlueScore = tempBlueScore;
                        }
                        else if(signDown.equals("+")) {
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore + Integer.valueOf(down);
                        }
                        else if(signDown.equals("-")){
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore - Integer.valueOf(down);
                        }
                        else
                            BlueScore = BlueScore;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }

                private void onLeftSwipe() {
                    if(initialStart) {
                        if(left.isEmpty())
                            BlueScore = BlueScore;
                        else if(left.equals("undo")) {
                            BlueScore = tempBlueScore;
                        }
                        else if(signLeft == "+") {
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore + Integer.valueOf(left);
                        }
                        else if(signLeft == "-") {
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore - Integer.valueOf(left);
                        }
                        else
                            BlueScore = BlueScore;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }

                private void onRightSwipe() {
                    if(initialStart) {
                        if(right.isEmpty())
                            BlueScore = BlueScore;
                        else if(right.equals("undo")) {
                            BlueScore = tempBlueScore;
                        }
                        else if(signRight.equals("+")) {
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore + Integer.valueOf(right);
                        }
                        else if(signRight.equals("-")){
                            tempBlueScore = BlueScore;
                            BlueScore = BlueScore - Integer.valueOf(right);
                        }
                        else
                            BlueScore = BlueScore;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }
    });
        red.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downX = event.getX();
                        downY = event.getY();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        upX = event.getX();
                        upY = event.getY();

                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        // swipe horizontal?
                        if (Math.abs(deltaX) > Math.abs(deltaY)) {
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                // left or right
                                if (deltaX < 0) {
                                    this.onRightSwipe();
                                    return true;
                                }
                                if (deltaX > 0) {
                                    this.onLeftSwipe();
                                    return true;
                                }
                            } else {
                                if(initialStart == true) {
                                    tempRedScore = RedScore;
                                    RedScore++;
                                    red.setText(String.valueOf(RedScore));
                                }
                            }
                        }
                        // swipe vertical?
                        else {
                            if (Math.abs(deltaY) > MIN_DISTANCE) {
                                // top or down
                                if (deltaY < 0) {
                                    this.onDownSwipe();
                                    return true;
                                }
                                if (deltaY > 0) {
                                    this.onUpSwipe();
                                    return true;
                                }
                            } else {
                                if(initialStart == true) {
                                    tempRedScore = RedScore;
                                    RedScore++;
                                    red.setText(String.valueOf(RedScore));
                                }
                            }
                        }

                        return true;
                    }
                }
                return false;
            }

            private void onUpSwipe() {
                if(initialStart) {
                    if(up.isEmpty())
                        BlueScore = BlueScore;
                    else if(up.equals("undo")) {
                        RedScore = tempRedScore;
                    }
                    else if(signUp == "+") {
                        tempRedScore = RedScore;
                        RedScore = RedScore + Integer.valueOf(up);
                    }
                    else if(signUp == "-"){
                        tempRedScore = RedScore;
                        RedScore = RedScore - Integer.valueOf(up);
                    }
                    else
                        RedScore = RedScore;
                    red.setText(String.valueOf(RedScore));
                }
            }

            private void onDownSwipe() {
                if(initialStart) {
                    if(down.isEmpty())
                        BlueScore = BlueScore;
                    else if(down.equals("undo")) {
                        RedScore = tempRedScore;
                    }
                    else if(signDown == "+") {
                        tempRedScore = RedScore;
                        RedScore = RedScore + Integer.valueOf(down);
                    }
                    else if(signDown == "-"){
                        tempRedScore = RedScore;
                        RedScore = RedScore - Integer.valueOf(down);
                    }
                    else
                        RedScore = RedScore;
                    red.setText(String.valueOf(RedScore));
                }
            }

            private void onLeftSwipe() {
                if(initialStart) {
                    if(left.isEmpty())
                        BlueScore = BlueScore;
                    else if(left.equals("undo")) {
                        RedScore = tempRedScore;
                    }
                    else if(signLeft == "+") {
                        tempRedScore = RedScore;
                        RedScore = RedScore + Integer.valueOf(left);
                    }
                    else if(signLeft == "-"){
                        tempRedScore = RedScore;
                        RedScore = RedScore - Integer.valueOf(left);
                    }
                    else
                        RedScore = RedScore;
                    red.setText(String.valueOf(RedScore));
                }
            }

            private void onRightSwipe() {
                if(initialStart) {
                    if(right.isEmpty())
                        BlueScore = BlueScore;
                    else if(right.equals("undo")) {
                        RedScore = tempRedScore;
                    }
                    else if(signRight == "+") {
                        tempRedScore = RedScore;
                        RedScore = RedScore + Integer.valueOf(right);
                    }
                    else if(signRight == "-"){
                        tempRedScore = RedScore;
                        RedScore = RedScore - Integer.valueOf(right);
                    }
                    else
                        RedScore = RedScore;
                    red.setText(String.valueOf(RedScore));
                }
            }
        });

        //Options listener
        if(!started) {
            final Intent i = new Intent(MainActivity.this, OptionsActivity.class);
            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!started) {
                        data = saveData();
                        i.putExtras(data);
                        startActivityForResult(i, 1);
                    }
                }
            });
        }
    }

    private Bundle saveData() {
        Bundle data = new Bundle();
        data.putInt("BlueScore",BlueScore);
        data.putInt("RedScore",RedScore);
        data.putLong("secondsLeft",secondsLeft);
        return data;
    }

    class Timer {
        public long milliseconds;
        private long countDownInterval;
        private boolean status;

        public Timer(long pMilliseconds, long pCountDownInterval) {
            this.milliseconds = pMilliseconds;
            this.countDownInterval = pCountDownInterval;
            status = false;
        }

        public void setTime(long newTime){
            milliseconds = newTime;
        }

        public void Start()
        {
            status = true;
            final Handler handler = new Handler();
            final Runnable counter = new Runnable(){

                public void run(){
                    secondsLeft = milliseconds;
                    if(milliseconds > 0 && status) {
                        long sec = (milliseconds%60000)/1000;
                        long minutes = milliseconds/60000;
                        String seconds;
                        if (sec < 10) {
                            seconds = "0" + String.valueOf(sec);
                        }
                        else {
                            seconds = String.valueOf(sec);
                        }
                        time.setText(minutes + ":" + seconds);
                        if(seconds == "0:00")
                            initialStart = false;
                        milliseconds -= countDownInterval;
                        handler.postDelayed(this, countDownInterval);
                    }
                    else
                    {
                        if(milliseconds <= 0)
                            time.setText("0:00");
                        status = false;
                        initialStart = false;
                        started = false;
                    }
                }
            };

            handler.postDelayed(counter, countDownInterval);
        }

        public void Stop(){
            status = false;
        }
    }

}



