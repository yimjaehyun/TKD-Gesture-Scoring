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

    private static TextView blue, red;
    private int BlueScore, RedScore, tempBlueScore, tempRedScore;
    private TextView time;
    private ImageButton start, options, lock;
    private boolean initialStart, started;
    private long secondsLeft;

    static final String logTag = "ActivitySwipeDetector";
    private Activity activity;
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
        secondsLeft = 5000;
        final Timer T = new Timer(secondsLeft, 1000);
        time = (TextView) findViewById(R.id.Timer);

        blue.setText(String.valueOf(BlueScore));
        red.setText(String.valueOf(RedScore));
//        long min = secondsLeft/60000;
//        long sec = secondsLeft%60000 / 1000;
//        time.setText(String.valueOf(min) + ":" + String.valueOf(sec));

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
                initialStart = !initialStart;
            }
        });

        //Options listener
        if(!started) {
            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!started)
                        startActivity(new Intent(MainActivity.this, OptionsActivity.class));
                }
            });
        }

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
                        tempBlueScore = BlueScore;
                        BlueScore = BlueScore + 3;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }

                private void onDownSwipe() {
                    if(initialStart) {
                        tempBlueScore = BlueScore;
                        BlueScore--;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }

                private void onLeftSwipe() {
                    if(initialStart) {
                        BlueScore = tempBlueScore;
                        blue.setText(String.valueOf(BlueScore));
                    }
                }

                private void onRightSwipe() {

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
                if(initialStart == true) {
                    tempRedScore = RedScore;
                    RedScore = RedScore + 3;
                    red.setText(String.valueOf(RedScore));
                }
            }

            private void onDownSwipe() {
                if(initialStart == true) {
                    tempRedScore = RedScore;
                    RedScore--;
                    red.setText(String.valueOf(RedScore));
                }
            }

            private void onLeftSwipe() {
                if(initialStart == true) {
                    RedScore = tempRedScore;
                    red.setText(String.valueOf(RedScore));
                }
            }

            private void onRightSwipe() {

            }
        });
    }

    class Timer {
        private long milliseconds;
        private long countDownInterval;
        private boolean status;

        public Timer(long pMilliseconds, long pCountDownInterval) {
            this.milliseconds = pMilliseconds;
            this.countDownInterval = pCountDownInterval;
            status = false;
        }
        public void Start()
        {
            status = true;
            final Handler handler = new Handler();
            final Runnable counter = new Runnable(){

                public void run(){
                    if(milliseconds > 0 && status) {
                        long sec = milliseconds/1000;
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



