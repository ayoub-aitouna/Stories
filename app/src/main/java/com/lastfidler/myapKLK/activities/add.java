package com.lastfidler.myapKLK.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.lastfidler.myapKLK.list.online_list;
import com.lastfidler.myapKLK.list.user;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class add extends AppCompatActivity implements View.OnClickListener {
    int PIC_IMAGE = 22;
    FloatingActionButton image;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    Button upload;
    EditText text;
    TextView name;
    ImageView image_;
    Uri filepath;
    Uri image_link;
    user user;
    StorageReference storageReference;
    CircleImageView profile;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        storageReference = FirebaseStorage.getInstance().getReference();
        collectionReference = db.collection("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        inisialize();
        getUser();
        image_.setVisibility(View.GONE);
        db = FirebaseFirestore.getInstance();
        image.setOnClickListener(this);
        upload.setOnClickListener(this);

    }

    private void getUser() {
        Query query = collectionReference.whereEqualTo("id", currentUser.getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    user = item.toObject(com.lastfidler.myapKLK.list.user.class);
                }
                assert user != null;
                name.setText(user.getName());
                Picasso.get().load(user.getUrl()).into(profile);
            }
        });
    }

    private void inisialize() {
        image_ = findViewById(R.id.image_);
        upload = findViewById(R.id.upload_button);
        image = findViewById(R.id.image);
        text = findViewById(R.id.post);
        name = findViewById(R.id.name_profile);
        profile = findViewById(R.id.profile_image_);
    }


    private void post_data() {
        String post = text.getText().toString();
        if (post.trim().matches("")) {
            Toast.makeText(this
                    , "please write something", Toast.LENGTH_LONG).show();

        } else {
            if (user != null) {
                CollectionReference blogref = FirebaseFirestore.getInstance()
                        .collection("Notebook");
                Map<String, Object> User = new HashMap<>();
                User.put("id", user.getId());
                User.put("name", user.getName());
                User.put("post", post);
                User.put("image", image_link.toString());
                User.put("like", 0);
                blogref.add(User);
                Toast.makeText(this, "uploading", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, "unkown", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == upload.getId()) {
            if (currentUser != null) {
                post_data();
            } else {
                Toast.makeText(this, "user is null ", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId() == image.getId()) {
            uploadimage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE
                && resultCode == Activity.RESULT_OK
                && data != null) {
            filepath = data.getData();
            final ProgressDialog progress = new ProgressDialog(this);
            progress.show();
            Toast.makeText(this, "" + filepath, Toast.LENGTH_SHORT).show();
            StorageReference ref = storageReference.child("post").child("Image/" + filepath.getLastPathSegment());
            ref.putFile(filepath).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.setMessage("+++");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(add.this, "fileur", Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
            });
            if (image_link != null) {
                image_.setVisibility(View.VISIBLE);
                Picasso.get().load(image_link.toString()).resize(100, 100).into(image_);
            }
        } else {
            Toast.makeText(this, "0x0", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadimage() {

        Intent up = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(up, PIC_IMAGE);
    }
}
