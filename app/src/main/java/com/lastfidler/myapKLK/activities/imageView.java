package com.lastfidler.myapKLK.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.lastfidler.myapKLK.R;
import com.squareup.picasso.Picasso;

public class imageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent get = getIntent();
        String link = get.getStringExtra("imageid");
        ImageView image = findViewById(R.id.ImageView);
        if (link != null) {
            Picasso.get().load(link).into(image);
        }
    }
}
