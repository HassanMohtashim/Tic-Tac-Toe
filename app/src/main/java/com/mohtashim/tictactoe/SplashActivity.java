package com.mohtashim.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    TextView tic, tac, toe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tic = findViewById(R.id.txtTic);
        tac = findViewById(R.id.txtTac);
        toe = findViewById(R.id.txtToe);
        //calling function for setting animations
        setAnimationsOnTextViews(tic,tac,toe);
        //setting handler for 3 sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    public void setAnimationsOnTextViews(TextView one, TextView two, TextView three){
        //setting animation on first text View
        Animation animationLeftToCenter = AnimationUtils.loadAnimation(this, R.anim.left_to_position_animation);
        one.startAnimation(animationLeftToCenter);
        //setting animation on third text View
        Animation animationRightToCenter = AnimationUtils.loadAnimation(this, R.anim.right_to_position_animation);
        two.startAnimation(animationRightToCenter);
        //setting animation on second text view
        three.startAnimation(animationLeftToCenter);
    }
}