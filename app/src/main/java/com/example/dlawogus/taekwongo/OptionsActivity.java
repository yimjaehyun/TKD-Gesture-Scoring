package com.example.dlawogus.taekwongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

public class OptionsActivity extends AppCompatActivity {

    private ImageButton BackButton;
    private EditText editBlueScore, editRedScore, SwipeUp, SwipeDown, SwipeLeft, SwipeRight, editTime;
    private Switch SwitchUp, SwitchDown, SwitchLeft, SwitchRight;
    private int BlueScore, RedScore;
    private long secondsLeft;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        restoreData();

        SwitchUp = (Switch) findViewById(R.id.signUp);
        SwitchDown = (Switch) findViewById(R.id.signDown);
        SwitchLeft = (Switch) findViewById(R.id.signLeft);
        SwitchRight = (Switch) findViewById(R.id.signRIght);
        editBlueScore = (EditText) findViewById(R.id.editBlueScore);
        editRedScore = (EditText) findViewById(R.id.editRedScore);
        SwipeUp = (EditText) findViewById(R.id.editSwipeUp);
        SwipeDown = (EditText) findViewById(R.id.editSwipeDown);
        SwipeLeft = (EditText) findViewById(R.id.editSwipeLeft);
        SwipeRight = (EditText) findViewById(R.id.editSwipeRight);
        editTime = (EditText) findViewById(R.id.editTime);
        save = (Button) findViewById(R.id.save);

        Bundle bundle = getIntent().getExtras();

        BackButton = (ImageButton) findViewById(R.id.back);

        //backbutton
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = getIntent().getExtras();
                Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
                data = saveData();
                intent.putExtras(data);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });

        //save listener
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.BlueScore = Integer.valueOf(String.valueOf(editBlueScore.getText()));
                MainActivity.blue.setText(String.valueOf(MainActivity.BlueScore));
                MainActivity.RedScore = Integer.valueOf(String.valueOf(editRedScore.getText()));
                MainActivity.red.setText(String.valueOf(MainActivity.RedScore));
            }
        });

        editBlueScore.setText(String.valueOf(BlueScore));
        editRedScore.setText(String.valueOf(RedScore));
        editTime.setText(String.valueOf((secondsLeft/1000) + 1));
        SwipeUp.setText(MainActivity.up);
        SwipeDown.setText(MainActivity.down);
        SwipeLeft.setText(MainActivity.left);
        SwipeRight.setText(MainActivity.right);

        if(MainActivity.signUp == "+")
            SwitchUp.setChecked(true);
        else if(MainActivity.signUp == "-")
            SwitchUp.setChecked(false);

        if(MainActivity.signDown == "+")
            SwitchDown.setChecked(true);
        else if(MainActivity.signDown == "-")
            SwitchDown.setChecked(false);

        if(MainActivity.signLeft == "+")
            SwitchLeft.setChecked(true);
        else if(MainActivity.signLeft == "-")
            SwitchLeft.setChecked(false);

        if(MainActivity.signRight == "+")
            SwitchRight.setChecked(true);
        else if(MainActivity.signRight == "-")
            SwitchRight.setChecked(false);
    }

    private void restoreData() {
        Bundle data = getIntent().getExtras();
        BlueScore = data.getInt("BlueScore");
        RedScore = data.getInt("RedScore");
        secondsLeft = data.getLong("secondsLeft");
    }

    private Bundle saveData() {
        Bundle data = new Bundle();
        data.putInt("BlueScore",BlueScore);
        data.putInt("RedScore",RedScore);
        data.putLong("secondsLeft",secondsLeft);
        return data;
    }


}
