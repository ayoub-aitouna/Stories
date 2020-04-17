package com.lastfidler.myapKLK.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.list.online_list;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_adapter extends RecyclerView.Adapter<profile_adapter.Myholder> {
    private Myholder.click mclick;
    private ArrayList<online_list> model;
    private Context context;

    public profile_adapter(ArrayList<online_list> model, Context context, Myholder.click click) {
        this.mclick = click;
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_indevdial, parent, false);
        return new Myholder(view, mclick);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.post.setText(String.valueOf(model.get(position).getPost()));
        holder.copy.setBackgroundResource(R.drawable.copy);
        holder.name.setText(String.valueOf(model.get(position).getName()));
        Picasso.get().load(model.get(position).getProfile())
                .resize(50, 50).into(holder.profile);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Firestorui_adapter.MyHolder.click mclick;
        CircleImageView profile;
        TextView name;
        TextView post;
        TextView likes;
        Button share;
        Button fav;
        Button copy;
        ImageButton view;
        ImageView imageview;

        public Myholder(@NonNull View itemView, click mclick) {
            super(itemView);
            imageview = itemView.findViewById(R.id.image);
            profile = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name);
            post = itemView.findViewById(R.id.text);
            likes = itemView.findViewById(R.id.likes);
            view = itemView.findViewById(R.id.view);
            share = itemView.findViewById(R.id.share);
            copy = itemView.findViewById(R.id.copy);
            fav = itemView.findViewById(R.id.favorit);
            view.setOnClickListener(this);
            copy.setOnClickListener(this);
            share.setOnClickListener(this);
            fav.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public interface click {
            void like(int position);

            void share(int position);

            void copy(int position);

            void fav(int position);
        }
    }
}
