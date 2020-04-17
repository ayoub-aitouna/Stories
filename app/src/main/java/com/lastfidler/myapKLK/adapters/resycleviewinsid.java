package com.lastfidler.myapKLK.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lastfidler.myapKLK.R;

import java.util.ArrayList;

public class resycleviewinsid extends RecyclerView.Adapter<resycleviewinsid.MyViewHolder> {
    private ArrayList arrayList;
    private LayoutInflater minflater;
    MyViewHolder.clickme clickme;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        clickme clickme;
        TextView textView;
        Button favoritebut;
        public boolean click = true;
        Button sharebut;
        Button copybut;

        public MyViewHolder(View v, clickme clickme) {
            super(v);
            this.clickme = clickme;
            textView = v.findViewById(R.id.text);
            favoritebut = v.findViewById(R.id.favorit);
            sharebut = v.findViewById(R.id.share);
            copybut = v.findViewById(R.id.copy);

            sharebut.setOnClickListener(this);
            copybut.setOnClickListener(this);
            favoritebut.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == sharebut.getId()) {
                clickme.share(getAdapterPosition());
            }
            if (v.getId() == copybut.getId()) {
                if (click) {
                    copybut.setBackgroundResource(R.drawable.done);
                    click = false;
                } else {
                    copybut.setBackgroundResource(R.drawable.copy);
                    click = true;
                }
                clickme.copay(getAdapterPosition());
            }
            if (v.getId() == favoritebut.getId()) {

                clickme.add(getAdapterPosition());
            }
        }

        public interface clickme {
            void share(int position);

            void copay(int position);

            void add(int position);

        }
    }

    public resycleviewinsid(Context context, ArrayList data, MyViewHolder.clickme clickme) {
        this.minflater = LayoutInflater.from(context);
        arrayList = data;
        this.clickme = clickme;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.rowfavorites, parent, false);

        return new MyViewHolder(view, clickme);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(String.valueOf(arrayList.get(position)));


    }

    public int getItemCount() {
        return arrayList.size();
    }


}

