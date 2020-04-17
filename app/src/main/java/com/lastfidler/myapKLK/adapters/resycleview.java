package com.lastfidler.myapKLK.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.list.list;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class resycleview extends RecyclerView.Adapter<resycleview.MyViewHolder> {
    private ArrayList<list> arrayList;
    private LayoutInflater minflater;
    private MyViewHolder.onmnchurlistener monmnchurlistener;
    public Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        onmnchurlistener onmnchurlistener;
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View v, onmnchurlistener onmnchurlistener) {
            super(v);
            this.onmnchurlistener = onmnchurlistener;
            textView = v.findViewById(R.id.textview);
            imageView = v.findViewById(R.id.appCompatImageView3);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == v.getId()) {
                onmnchurlistener.click(getAdapterPosition());
            }


        }

        public interface onmnchurlistener {
            void click(int position);


        }
    }

    public resycleview(Context context, ArrayList<list> data, MyViewHolder.onmnchurlistener onmnchurlistener) {
        this.minflater = LayoutInflater.from(context);
        arrayList = data;
        this.context = context;
        this.monmnchurlistener = onmnchurlistener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.rowview, parent, false);

        return new MyViewHolder(view, monmnchurlistener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.textView.setText(String.valueOf(arrayList.get(position).name));
        if (arrayList.get(position).images != null) {
            Picasso.get().load(arrayList.get(position).images).resize(500
                    , 500).into(holder.imageView);
        }


    }

    public int getItemCount() {
        return arrayList.size();
    }


}

