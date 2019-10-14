package com.zjt.mythirdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    TextView scorea,scoreb;
    Button add1a,add2a,add3a,add1b,add2b,add3b,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        scorea=findViewById(R.id.textView);
        scoreb=findViewById(R.id.textView);
        add1a=findViewById(R.id.button);
        add2a=findViewById(R.id.button2);
        add3a=findViewById(R.id.button3);
        add1b=findViewById(R.id.button);
        add2b=findViewById(R.id.button2);
        add3b=findViewById(R.id.button3);
        submit=findViewById(R.id.button4);





    }
}
