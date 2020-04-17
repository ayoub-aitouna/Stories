package com.lastfidler.myapKLK.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.dialog.alertdialog;
import com.lastfidler.myapKLK.fragments.fav_fragment;
import com.lastfidler.myapKLK.fragments.home_fragment;
import com.lastfidler.myapKLK.fragments.luck_fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, alertdialog.exite {
    private DrawerLayout drawerLayout;
    public boolean bool = false;
    private InterstitialAd mInterstitialAd;
    GoogleSignInAccount account;
    NavigationView navigationView;
    //private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //stublishing an ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        // loading ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3563980099774915/4674131973");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.bare);
        setSupportActionBar(toolbar);
        Menu menu = navigationView.getMenu();
        View view = navigationView.inflateHeaderView(R.layout.head);
        drawerLayout = findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new home_fragment()).commit();

        }
    }

    //on backPressed exits the navigation bar if its open if not its calls alert dialog
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            dialog();
            if (bool)
                super.onBackPressed();
        }
    }

    //on item in navigation bar clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:
                mInterstitialAd.show();

                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new home_fragment()).commit();
                break;
            case R.id.favorit:
                mInterstitialAd.show();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new fav_fragment()).commit();

                break;

            case R.id.lucky:
                mInterstitialAd.show();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new luck_fragment()).commit();
                break;
            case R.id.share:
                mInterstitialAd.show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.lastfidler.myapKLK");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
            case R.id.ptavicy:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://sites.google.com/view/st0reis/accueil"));
                startActivity(i);
                break;
            case R.id.review:
                Intent review = new Intent(Intent.ACTION_VIEW);
                review.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.lastfidler.myapKLK"));
                startActivity(review);
                break;

        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        return true;
    }

    public void Singout() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    public void dialog() {
        alertdialog alertdialog = new alertdialog();
        alertdialog.show(getSupportFragmentManager(), "alertdialog");
    }

    //On_Yes Clicked in Alert_dialog its exits the app
    @Override
    public void onyesclick() {
        finish();
        System.exit(0);

    }


}
