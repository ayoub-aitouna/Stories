package com.lastfidler.myapKLK.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.adapters.comment_adapter;
import com.lastfidler.myapKLK.list.Comment;
import com.lastfidler.myapKLK.list.user;

import java.util.ArrayList;
import java.util.Collections;

public class comment extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    comment_adapter comment;
    RecyclerView recyclerView;
    EditText text;
    FloatingActionButton send;
    Intent get;
    String S_comment;
    String id;
    Query query;
    ArrayList<Comment> commentlist;
    CollectionReference collectionReference;
    private user User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getUser();
        setUp();
        getComments();
        adapter();
        setComment();
    }


    private void getUser() {
        collectionReference = db.collection("Users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        Query query = collectionReference.whereEqualTo("id", currentUser.getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    User = item.toObject(user.class);
                }
            }
        });
    }

    private void setUp() {
        collectionReference = db.collection("comment");
        get = getIntent();
        id = get.getStringExtra("id");
        send = findViewById(R.id.send);
        text = findViewById(R.id.comment_);
        send.setOnClickListener(this);
    }


    private void getComments() {
        commentlist = new ArrayList<>();
        query = collectionReference.whereEqualTo("id", id);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    commentlist.add(item.toObject(Comment.class));
                }
                Collections.reverse(commentlist);
                comment.notifyDataSetChanged();

            }
        });
    }

    private void adapter() {
        comment = new comment_adapter(commentlist);
        recyclerView = findViewById(R.id.comment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(comment);
    }

    private void setComment() {
        S_comment = text.getText().toString();
        if (!S_comment.trim().isEmpty()) {
            String url = "https://avatarfiles.alphacoders.com/867/86709.jpg";
            collectionReference.add(new Comment(url, id, S_comment));
            Toast.makeText(this, "sending", Toast.LENGTH_SHORT).show();
            text.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == send.getId()) {
            setComment();
            commentlist.clear();
            comment.notifyDataSetChanged();
        }
    }

}
