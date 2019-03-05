package com.dreytech.clientdreymart;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private  int waktu_loading = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent SignIn = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(SignIn);
                finish();
            }
        },waktu_loading);

    }
}
