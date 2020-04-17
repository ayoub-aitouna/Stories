package com.lastfidler.myapKLK.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.databases.database;
import com.lastfidler.myapKLK.databases.datafave;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lastfidler.myapKLK.R;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.CLIPBOARD_SERVICE;

public class luck_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public View view;
    database data;
    public SwipeRefreshLayout swipeRefreshLayout;
    datafave favorite;
    ArrayList arrayList;
    TextView textview;
    String text;
    boolean clicked = false;
    boolean copycheck = false;
    Button share;
    Button copy;
    Button like;
    private AdView ad1;
    private InterstitialAd mInterstitialAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //initializing variables
        view = inflater.inflate(R.layout.luck, container, false);
        share = view.findViewById(R.id.share);
        textview = view.findViewById(R.id.luck);
        copy = view.findViewById(R.id.copy);
        like = view.findViewById(R.id.favorit);
        ad1 = view.findViewById(R.id.adView1);


        //setting onwipereflesh
        swipeRefreshLayout = view.findViewById(R.id.luckreflesh);
        swipeRefreshLayout.setOnRefreshListener(this);


        //stublishing an ads
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        // loading banner
        AdRequest adRequest = new AdRequest.Builder().build();
        ad1.loadAd(adRequest);


        // intializing Interstitial ads
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3563980099774915/9487474952");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        // databases
        data = new database(getActivity());
        favorite = new datafave(getActivity());


        // sedata
        Random random = new Random();
        int i = random.nextInt(50);
        arrayList = data.gettext(i);
        text = String.valueOf(arrayList.get(0));
        textview.setText(text);


        share.setOnClickListener(this);
        copy.setOnClickListener(this);
        like.setOnClickListener(this);
        return view;
    }

    @Override
    public void onRefresh() {

        arrayList = new ArrayList();
        // sedata
        Random random = new Random();
        int i = random.nextInt(50);
        arrayList = data.gettext(i);
        text = String.valueOf(arrayList.get(1));
        textview.setText(text);
        like.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        copy.setBackgroundResource(R.drawable.copy);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == share.getId()) {
            mInterstitialAd.show();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        if (v.getId() == copy.getId()) {
            if (copycheck) copycheck = false;
            else copycheck = true;
            if (copycheck)
                copy.setBackgroundResource(R.drawable.done);
            else
                copy.setBackgroundResource(R.drawable.copy);

            mInterstitialAd.show();
            ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);
        }
        if (v.getId() == like.getId()) {
            if (clicked) clicked = false;
            else clicked = true;
            if (clicked) {
                mInterstitialAd.show();
                favorite.insert(text);
                like.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            } else {
                mInterstitialAd.show();
                like.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                favorite.delet(text);
            }
        }
    }


}
