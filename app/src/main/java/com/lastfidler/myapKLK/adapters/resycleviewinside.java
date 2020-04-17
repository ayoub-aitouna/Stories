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

public class resycleviewinside extends RecyclerView.Adapter<resycleviewinside.MyViewHolder> {
    private ArrayList arrayList;
    private LayoutInflater minflater;
    Context context;
    MyViewHolder.clickme clickme;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        clickme clickme;
        TextView textView;
        Button favoritebut;
        Button sharebut;
        Button copybut;
        public boolean click = true;

        public MyViewHolder(View v, clickme clickme) {
            super(v);

            this.clickme = clickme;

            //inisializing variables
            textView = v.findViewById(R.id.text);
            favoritebut = v.findViewById(R.id.favorit);
            sharebut = v.findViewById(R.id.share);
            copybut = v.findViewById(R.id.copy);


            //CLick event
            sharebut.setOnClickListener(this);
            copybut.setOnClickListener(this);
            favoritebut.setOnClickListener(this);
            textView.setOnClickListener(this);

        }


        //onclick event
        @Override
        public void onClick(View v) {
            if (v.getId() == sharebut.getId()) {
                clickme.share(getAdapterPosition());
            }
            if (v.getId() == copybut.getId()) {
                clickme.copay(getAdapterPosition());
                if (click) {
                    copybut.setBackgroundResource(R.drawable.done);
                    click = false;
                } else {
                    copybut.setBackgroundResource(R.drawable.copy);
                    click = true;
                }
            }
            if (v.getId() == favoritebut.getId()) {
                if (click) click = false;
                else click = true;
                if (click) {
                    favoritebut.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    clickme.add(getAdapterPosition());


                } else {
                    favoritebut.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    clickme.delet(getAdapterPosition());


                }

            }
            if (v.getId() == textView.getId()) {
                clickme.viewclick(getAdapterPosition());
            }

        }



        //interface that have been used to setClickonlistener on elemenrts
        public interface clickme {
            void share(int position);

            void copay(int position);

            void add(int position);

            void delet(int position);

            void viewclick(int posision);

        }
    }


    //myConstracture
    public resycleviewinside(Context context, ArrayList data, MyViewHolder.clickme clickme) {
        this.minflater = LayoutInflater.from(context);
        arrayList = data;
        this.clickme = clickme;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.rowmessage, parent, false);
        return new MyViewHolder(view, clickme);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(String.valueOf(arrayList.get(position)));
        holder.favoritebut.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);


    }

    public int getItemCount() {
        return arrayList.size();
    }


}

