package com.lastfidler.myapKLK.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.adapters.resycleviewinsid;
import com.lastfidler.myapKLK.databases.datafave;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.CLIPBOARD_SERVICE;

public class fav_fragment extends Fragment implements com.lastfidler.myapKLK.adapters.resycleviewinsid.MyViewHolder.clickme {
    public resycleviewinsid resycleviewinsid;
    public ArrayList arraylist;
    RecyclerView.LayoutManager layoutManager;
    public datafave fb;
    private AdView ad1;
    private InterstitialAd mInterstitialAd;
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        //stublishing an ads
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // loading banner
        ad1 = view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad1.loadAd(adRequest);

        // intializing Interstitial ads
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3563980099774915/2842175551");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        fb = new datafave(getActivity());
        arraylist = fb.getfav();
        Collections.reverse(arraylist);
        resycleviewinsid = new resycleviewinsid(getActivity(), arraylist, this);
        recyclerView = view.findViewById(R.id.favrecy);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setFocusable(false);

        recyclerView.setAdapter(resycleviewinsid);
        return view;
    }

    @Override
    public void share(int position) {
        mInterstitialAd.show();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "" + arraylist.get(position));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void copay(int position) {
        mInterstitialAd.show();
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", "" + arraylist.get(position));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "copied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void add(int position) {
        mInterstitialAd.show();
        fb.delet(String.valueOf(arraylist.get(position)));
        arraylist = fb.getfav();
        resycleviewinsid = new resycleviewinsid(getActivity(), arraylist, this);
        recyclerView.setAdapter(resycleviewinsid);


    }
}
