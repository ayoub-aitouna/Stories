package com.lastfidler.myapKLK.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.list.online_list;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Firestorui_adapter extends FirestoreRecyclerAdapter<online_list, Firestorui_adapter.MyHolder> {
    private static MyHolder.click click;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Firestorui_adapter(@NonNull FirestoreRecyclerOptions<online_list> options, MyHolder.click onclick) {
        super(options);
        Firestorui_adapter.click = onclick;

    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull online_list model) {
        holder.post.setText(String.valueOf(model.getPost()));
        holder.copy.setBackgroundResource(R.drawable.copy);
        holder.name.setText(String.valueOf(model.getName()));
        Picasso.get().load(model.getProfile())
                .resize(50, 50).into(holder.profile);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_indevdial, parent, false);
        return new MyHolder(view, click);
    }

    public String getpostid(int position) {
        return getSnapshots().getSnapshot(position).getId();
    }

    //public String copy(int position) {
    // }

    // public String profile(int position) {
    // }


    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        click mclick;
        CircleImageView profile;
        TextView name;
        TextView post;
        TextView likes;
        boolean clicked = true;
        Button share;
        Button fav;
        Button copy;
        ImageButton view;


        MyHolder(@NonNull View itemView, click mclick) {
            super(itemView);
            this.mclick = mclick;
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
            profile.setOnClickListener(this);
            name.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            if (v.getId() == view.getId()) {
                click.like(getAdapterPosition());


            }
            if (v.getId() == copy.getId()) {
                click.copy(getAdapterPosition());
                if (clicked) {
                    copy.setBackgroundResource(R.drawable.done);
                    clicked = false;
                } else {
                    copy.setBackgroundResource(R.drawable.copy);
                    clicked = true;
                }

            }
            if (v.getId() == share.getId()) {
                click.share(getAdapterPosition());
            }
            if (v.getId() == fav.getId()) {
                click.fav(getAdapterPosition());
            }
            if (v.getId() == profile.getId() || v.getId() == name.getId()) {
                click.profile(getAdapterPosition());
            }


        }

        public interface click {
            void like(int position);

            void share(int position);

            void copy(int position);

            void fav(int position);

            void profile(int position);
        }


    }


}
