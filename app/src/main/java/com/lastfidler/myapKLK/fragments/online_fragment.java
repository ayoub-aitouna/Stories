package com.lastfidler.myapKLK.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.activities.add;
import com.lastfidler.myapKLK.activities.comment;
import com.lastfidler.myapKLK.adapters.Firestorui_adapter;


import com.lastfidler.myapKLK.databases.datafave;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.lastfidler.myapKLK.list.online_list;


public class online_fragment extends Fragment implements View.OnClickListener, Firestorui_adapter.MyHolder.click {
    private View view;
    private InterstitialAd mInterstitialAd;
    private FirebaseFirestore dbui = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = dbui.collection("Notebook");
    private Firestorui_adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.online_layout, container, false);
        datafave datafave = new datafave(getActivity());
        FloatingActionButton fbutton = view.findViewById(R.id.add);
        ads();
        setUpRecyclerView();
        fbutton.setOnClickListener(this);
        return view;
    }


    private void setUpRecyclerView() {
        Query query = notebookRef.orderBy("likes", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<online_list> options = new FirestoreRecyclerOptions.Builder<online_list>()
                .setQuery(query, online_list.class).build();
        adapter = new Firestorui_adapter(options, this);
        RecyclerView recyclerView = view.findViewById(R.id.lister);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void ads() {
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView ad1 = view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad1.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3563980099774915/8365964972");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), add.class));
    }


    @Override
    public void like(int position) {

    }

    @Override
    public void share(int position) {
        mInterstitialAd.show();
    }

    @Override
    public void copy(int position) {
        mInterstitialAd.show();


    }

    @Override
    public void fav(int position) {
        String id = adapter.getpostid(position);
        Intent set = new Intent(getActivity(), comment.class);
        set.putExtra("id", id);
        startActivity(set);

    }

    @Override
    public void profile(int position) {

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

