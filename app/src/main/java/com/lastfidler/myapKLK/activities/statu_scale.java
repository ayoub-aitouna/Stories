package com.lastfidler.myapKLK.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.databases.datafave;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class statu_scale extends AppCompatActivity implements View.OnClickListener {

    //declaring some variables
    public String statue;
    public datafave datafave;
    Button copy;
    private AdView ad1;
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statu_scale);
        //stublishing an ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // loading banner
        ad1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad1.loadAd(adRequest);

        //declaing an Object from datafave class
        datafave = new datafave(this);


        //geting the String variable from intent
        Intent i = getIntent();
        statue = i.getStringExtra("statue");


        //declaring and initialising textview and setting the String to it
        TextView textView = findViewById(R.id.text);
        textView.setText(statue);

        //getting buttons from view
        copy = findViewById(R.id.copy);
        share = findViewById(R.id.share);

        //on_on buttons
        copy.setOnClickListener(this);
        share.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", "" + statue);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, statue);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;

            default:
                // add Intestelar ads
                break;
        }

    }


}
