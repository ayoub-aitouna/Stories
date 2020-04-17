package com.lastfidler.myapKLK.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.adapters.resycleviewinside;
import com.lastfidler.myapKLK.databases.database;
import com.lastfidler.myapKLK.databases.datafave;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class statu_browser extends AppCompatActivity implements resycleviewinside.MyViewHolder.clickme, SwipeRefreshLayout.OnRefreshListener {
    int id;
    public SwipeRefreshLayout swipeRefreshLayout;
    resycleviewinside resycleviewinside;
    ArrayList dataarray;
    database data;
    datafave fb;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private AdView ad1;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statu_browser);


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




        // intializing Interstitial ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3563980099774915/6098894285");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());





        // getting data from intent
        Intent i = getIntent();
        id = i.getIntExtra("key", 0) + 1;




        // intializing object from databases classes
        data = new database(this);
        fb = new datafave(this);



        //setting database
        dataarray = data.gettext(id);
        Collections.shuffle(dataarray);

        //inisializing recycelerviewadapter
        resycleviewinside = new resycleviewinside(statu_browser.this, dataarray, this);


        //initializing recyceler view
        recyclerView = findViewById(R.id.recy_browser);
        layoutManager = new LinearLayoutManager(statu_browser.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(resycleviewinside);





        //onswiperefresh
        swipeRefreshLayout = findViewById(R.id.browser);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void share(int position) {
        mInterstitialAd.show();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "" + dataarray.get(position));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }

    @Override
    public void copay(int position) {
        mInterstitialAd.show();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", "" + dataarray.get(position));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void add(int position) {
        mInterstitialAd.show();
        fb.insert("" + dataarray.get(position));
    }

    @Override
    public void delet(int position) {
        fb.delet(String.valueOf(dataarray.get(position)));

    }

    @Override
    public void viewclick(int posision) {
        mInterstitialAd.show();
        Intent i = new Intent(getApplicationContext(), statu_scale.class);
        i.putExtra("statue", "" + dataarray.get(posision));
        startActivity(i);
        //add intistilare ads

    }

    @Override
    public void onRefresh() {
        dataarray = new ArrayList();
        mInterstitialAd.show();
        //setting database
        dataarray = data.gettext(id);
        Collections.shuffle(dataarray);




        //initializing recyceler view
        recyclerView = findViewById(R.id.recy_browser);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setFocusable(false);

        //inisializing recycelerviewadapter
        resycleviewinside resycleviewinside = new resycleviewinside(this, dataarray, this);
        recyclerView.setAdapter(resycleviewinside);
        swipeRefreshLayout.setRefreshing(false);


    }
}
