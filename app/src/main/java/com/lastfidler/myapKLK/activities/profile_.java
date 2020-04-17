package com.lastfidler.myapKLK.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.dialog.AreYouSure;
import com.lastfidler.myapKLK.list.user;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_ extends AppCompatActivity implements AreYouSure.sure, View.OnClickListener {
    private Intent get;
    private user user;
    private Button remove;
    private Button Message;
    private String id;
    private ImageView background;
    private CircleImageView profile;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        get = getIntent();
        id = get.getStringExtra("ID");
        GetUserById();
        SetUpuser();
        onimageclick();

    }

    private void onimageclick() {
        background = findViewById(R.id.background_image);
        profile = findViewById(R.id.profile_pic);
        background.setOnClickListener(this);
        profile.setOnClickListener(this);
    }

    private void SetUpuser() {
        TextView name = findViewById(R.id.name);
        CircleImageView pic = findViewById(R.id.profile_pic);
        ImageView background = findViewById(R.id.background_image);
        remove = findViewById(R.id.remove);
        //  name.setText(user.getName());
        //  Picasso.get().load(user.getUrl()).resize(100, 100).into(pic);
        //  Picasso.get().load(user.getUrl()).into(pic);
        remove.setOnClickListener(this);
    }

    private void GetUserById() {
        collectionReference = db.collection("Users");
        Query query = collectionReference.whereEqualTo("id", id);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    user = item.toObject(user.class);
                }
            }
        });
    }

    @Override
    public void Sure() {
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Intent set = new Intent(profile_.this, imageView.class);
        if (v.getId() == remove.getId()) {
            dialog();
        }
        if (v.getId() == background.getId()) {
            set.putExtra("imageid", "");
            startActivity(set);
        }
        if (v.getId() == profile.getId()) {
            set.putExtra("imageid", "");
            startActivity(set);
        }
    }

    private void dialog() {
        AreYouSure areYouSure = new AreYouSure();
        areYouSure.show(getSupportFragmentManager(), "AreYouSure");
        remove.setText("Add Freind");
        remove.setBackgroundResource(R.drawable.rounded);
        remove.setTextColor(12);

    }
}
