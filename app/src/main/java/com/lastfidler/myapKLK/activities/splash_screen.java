package com.lastfidler.myapKLK.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.lastfidler.myapKLK.R;


public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView textView = findViewById(R.id.from);
        TextView name = findViewById(R.id.name);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim);
        textView.startAnimation(animation);
        name.startAnimation(animation);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();

    }
}
