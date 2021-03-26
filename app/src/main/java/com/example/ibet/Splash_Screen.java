package com.example.ibet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Screen extends AppCompatActivity {

    ImageView splash;
    ImageView splash2;
    ImageView splash3;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        splash = findViewById(R.id.imageViewSplash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        splash.startAnimation(animation);

        splash2 = findViewById(R.id.SplashLogo);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        splash2.startAnimation(animation);

        splash3 = findViewById(R.id.SplashBall);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        splash3.startAnimation(animation);

        pref = this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);

                if(pref.getString("token",null)!=null)
                {
                    intent.putExtra("isLogin", true);
                }

                startActivity(intent);
                finish();
            }
        },4000);

    }
}