package com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.roomdb_mvvm.expandablerecyclerview.listeners.OnGroupClickListener;

public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnGroupClickListener listener;

    public GroupViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onGroupClick(getAdapterPosition());
        }
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    public void expand() {
    }

    public void collapse() {
    }
}
