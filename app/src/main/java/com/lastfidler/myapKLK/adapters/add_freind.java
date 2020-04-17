package com.lastfidler.myapKLK.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.list.freind;
import com.lastfidler.myapKLK.list.user;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class add_freind extends RecyclerView.Adapter<add_freind.myholder> {
    private ArrayList<user> friendlist;
    private LayoutInflater minflater;
    Context context;
    private myholder.Onclick monclick;

    public add_freind(ArrayList<user> friendlist, Context context, myholder.Onclick onclick) {
        this.minflater = LayoutInflater.from(context);
        this.friendlist = friendlist;
        this.context = context;
        this.monclick = onclick;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.add_freind_individial, parent, false);
        return new myholder(view, monclick);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        holder.name.setText(friendlist.get(position).getName());
        Picasso.get().load(friendlist.get(position).getUrl()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return friendlist.size();
    }

    public user getuser(int position) {
        return friendlist.get(position);
    }

    public static class myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Onclick monclick;
        TextView name;
        CircleImageView profile;
        Button add;

        public myholder(@NonNull View itemView, Onclick onclick) {
            super(itemView);
            this.monclick = onclick;
            name = itemView.findViewById(R.id.name);
            profile = itemView.findViewById(R.id.profile_pic);
            add = itemView.findViewById(R.id.add_freind);
            add.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == add.getId()) {
                add.setBackgroundResource(R.drawable.added);
                add.setText("Added");
                monclick.Onrequestsent(getAdapterPosition());
            }
        }

        public interface Onclick {
            void Onrequestsent(int position);
        }
    }
}
