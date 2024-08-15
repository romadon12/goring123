package com.example.apkdatamhs;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class loadingScreen extends AppCompatActivity {

    public static int SPLASH_TIMER = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        ImageView imageView = findViewById(R.id.imageView);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotateAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(loadingScreen.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}