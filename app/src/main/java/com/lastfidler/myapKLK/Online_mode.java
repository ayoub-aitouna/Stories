package com.lastfidler.myapKLK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lastfidler.myapKLK.fragments.online_fragment;
import com.lastfidler.myapKLK.fragments.profile;

public class Online_mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new online_fragment()).commit();
        }

    }
}
