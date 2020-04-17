package com.lastfidler.myapKLK.fragments;

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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.adapters.add_freind;
import com.lastfidler.myapKLK.list.user;

import java.util.ArrayList;

public class FriendRequest extends Fragment implements add_freind.myholder.Onclick {
    private ArrayList<user> user = new ArrayList<>();
    private View view;
    private String id;
    private add_freind adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<user> frends = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.freindrequest, container, false);
        FirebaseUser curentuser = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference ref_id = db.collection("Users");
        Query query = ref_id.whereEqualTo("id", curentuser.getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                String id_ = "";
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    id_ = item.getId();
                }
                id = id_;
            }
        });

        getFiredlist();

        GetUsers();
        getlist();
        SetUpRecyclerview();

        return view;
    }


    private void SetUpRecyclerview() {
        adapter = new add_freind(user, getActivity(), this);
        RecyclerView recyclerView = view.findViewById(R.id.add_freind);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void GetUsers() {
        CollectionReference collectionReference = db.collection("Users");
        Query query = collectionReference.orderBy("id", Query.Direction.ASCENDING);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot item : queryDocumentSnapshots) {
                    user.add(item.toObject(user.class));
                }
            }
        });
    }

    private void getFiredlist() {


    }

    private void getlist() {
        Toast.makeText(getActivity(), "" + id, Toast.LENGTH_SHORT).show();
        if (id != null) {
            ListenerRegistration ref = db.collection("Users")
                    .document(id).collection("freinds")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        }
                    });
        }
    }

    @Override
    public void Onrequestsent(int position) {
        user user = adapter.getuser(position);
        Task<Void> addref = db.collection("Users")
                .document(id).collection("freinds").document("1321")
                .set(user);
        Toast.makeText(getActivity(), "added", Toast.LENGTH_SHORT).show();
    }
}
