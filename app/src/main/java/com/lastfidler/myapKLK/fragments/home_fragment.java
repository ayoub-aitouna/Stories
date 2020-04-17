package com.lastfidler.myapKLK.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.activities.statu_browser;
import com.lastfidler.myapKLK.adapters.resycleview;
import com.lastfidler.myapKLK.databases.database;
import com.lastfidler.myapKLK.list.list;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_fragment extends Fragment implements resycleview.MyViewHolder.onmnchurlistener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList lista;
    private InterstitialAd mInterstitialAd;
    private database fb;
    private boolean isConnected;
    private ArrayList<String> DAtaList = new ArrayList<>();
    private resycleview res;
    private ProgressDialog dialog;
    private ArrayList<list> mylist = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        res = new resycleview(getActivity(), mylist, this);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading");
        dialog.show();

        ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork
                = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


        //geeting User


        //stublishing an ads
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // loading banner
        AdView ad1 = view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad1.loadAd(adRequest);


        // intializing Interstitial ads
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3563980099774915/8365964972");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        //recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.recycl);
        recyclerView.setFocusable(false);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.isAutoMeasureEnabled();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(res);


        //set data to arraylist
        fb = new database(getActivity());
        lista = fb.gettitle();
        //getting data ofline
        setoflinedata();

        //get Url
        dialog.show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference().child("images");
        myref.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DAtaList.add(data.getValue().toString());
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setoflinedata();
        //reflesh
        swipeRefreshLayout = view.findViewById(R.id.home);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }


    @Override
    public void click(int position) {
        mInterstitialAd.show();
        Intent i = new Intent(getActivity(), statu_browser.class);
        i.putExtra("key", position);
        startActivity(i);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mylist = new ArrayList<>();
        res.notifyDataSetChanged();
    }

    private void setoflinedata() {
        if (DAtaList.isEmpty())
            for (int i = 0; i < lista.size(); i++) {
                mylist.add(new list(String.valueOf(lista.get(i)), "sdfasdf"));
            }
        else
            for (int i = 0; i < lista.size(); i++) {
                mylist.add(new list(String.valueOf(lista.get(i)), DAtaList.get(i)));
            }

        res.notifyDataSetChanged();
        dialog.dismiss();
    }

}
