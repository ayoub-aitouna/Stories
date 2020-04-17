package com.lastfidler.myapKLK.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.lastfidler.myapKLK.R;
import com.squareup.picasso.Picasso;
import com.lastfidler.myapKLK.list.Comment;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class comment_adapter extends RecyclerView.Adapter<comment_adapter.myholder> {
    ArrayList<Comment> commentList;

    public comment_adapter(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_individial, parent, false);
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        Picasso.get().load(commentList.get(position).getUrl()).into(holder.photo);
        if (commentList.get(position) != null) {
            holder.post.setText(commentList.get(position).getComment());
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public class myholder extends RecyclerView.ViewHolder {
        CircleImageView photo;
        TextView post;

        public myholder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.comment_profile);
            post = itemView.findViewById(R.id.post_comment);
        }
    }

}
