package com.example.ubd.count.part1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ubd.count.R;

import java.util.Vector;

public class displayAdapter extends RecyclerView.Adapter<displayAdapter.ViewHolder> {

    public Vector<displayStrings> displayStrings = new Vector<>();


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView1;
        TextView textView2;
        private ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView1.setText(displayStrings.get(position).getString1());
        holder.textView2.setText(displayStrings.get(position).getString2());
    }

    @Override
    public int getItemCount() {
        return displayStrings.size();
    }
}
