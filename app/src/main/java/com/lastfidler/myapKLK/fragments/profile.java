package com.lastfidler.myapKLK.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.activities.add;
import com.lastfidler.myapKLK.activities.imageView;
import com.lastfidler.myapKLK.adapters.profile_adapter;
import com.lastfidler.myapKLK.list.online_list;
import com.lastfidler.myapKLK.list.user;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends Fragment implements profile_adapter.Myholder.click, View.OnClickListener {
    private static final int IMAGE_PICK = 22;
    private ImageView background;
    private CircleImageView profile;
    private FloatingActionButton edit;
    private Uri filepath;
    private View view;
    private profile_adapter adapter;
    private FirebaseUser currentUser;
    private FirebaseFirestore dbui = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = dbui.collection("Notebook");
    private ArrayList<online_list> model;
    private user user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);
        getuser();
        setUpRecyclerView();
        getdata();
        setOnclick();
        getUserdata();
        ButtonsFonction();
        return view;
    }

    private void ButtonsFonction() {
        Button add = view.findViewById(R.id.add);
        FloatingActionButton edit = view.findViewById(R.id.edit);
        add.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    private void getUserdata() {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users");
        Query query = collectionReference.whereEqualTo("id", currentUser.getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    user = item.toObject(com.lastfidler.myapKLK.list.user.class);
                }
            }
        });
    }

    private void setOnclick() {
        background = view.findViewById(R.id.background_image);
        profile = view.findViewById(R.id.profile_pic);
        edit = view.findViewById(R.id.edit);
        background.setOnClickListener(this);
        profile.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    private void getdata() {
        Query query = notebookRef.whereEqualTo("id", currentUser.getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    model.add(item.toObject(online_list.class));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getuser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    private void setUpRecyclerView() {
        model = new ArrayList<>();
        adapter = new profile_adapter(model, getActivity(), this);
        RecyclerView recyclerView = view.findViewById(R.id.profile_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.hasFixedSize();
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void like(int position) {

    }

    @Override
    public void share(int position) {

    }

    @Override
    public void copy(int position) {

    }

    @Override
    public void fav(int position) {

    }

    @Override
    public void onClick(View v) {
        Intent set = new Intent(getActivity(), imageView.class);
        if (v.getId() == background.getId()) {
            Toast.makeText(getActivity(), "background clicked", Toast.LENGTH_SHORT).show();
            assert user != null;
            set.putExtra("imageid", user.getUrl());
            startActivity(set);
        }
        if (v.getId() == profile.getId()) {
            Toast.makeText(getActivity(), "profile clicked", Toast.LENGTH_SHORT).show();
            assert user != null;
            set.putExtra("imageid", user.getUrl());
            startActivity(set);
        }
        if (v.getId() == edit.getId()) {
            upload();
        }
        if (v.getId() == R.id.add) {
            startActivity(new Intent(getActivity(), add.class));
        }
        if (v.getId() == R.id.edit) {
            Toast.makeText(getActivity(), "wait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            filepath = data.getData();
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("prolife")
                    .child("image/" + filepath.getLastPathSegment());
            ref.putFile(filepath).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploading", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "edited successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void upload() {
        Intent upload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(upload, IMAGE_PICK);
    }
}
