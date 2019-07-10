package com.example.mat.roomdb_mvvm.color.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.ui.OnColorClickListener;
import com.example.mat.roomdb_mvvm.databinding.ColorItemBinding;

public class ColorAdapter extends ListAdapter<Color, ColorAdapter.ColorHolder> {

    private OnColorClickListener onColorClickListener;

    public ColorAdapter(OnColorClickListener onColorClickListener) {
        super(DIFF_CALLBACK);
        this.onColorClickListener = onColorClickListener;
    }

    public static final DiffUtil.ItemCallback<Color> DIFF_CALLBACK = new DiffUtil.ItemCallback<Color>() {
        @Override
        public boolean areItemsTheSame(Color oldItem, Color newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Color oldItem, Color newItem) {
            return oldItem.getColorName().equals(newItem.getColorName()) &&
                    oldItem.getPrimaryColor() == newItem.getPrimaryColor() &&
                    oldItem.getPrimaryDarkColor() == newItem.getPrimaryDarkColor() &&
                    oldItem.getPrimaryLightColor() == newItem.getPrimaryLightColor();
        }
    };


    @NonNull
    @Override
    public ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_item, parent, false);
        return new ColorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorHolder holder, int position) {
        Color color = getItem(position);
        holder.setClickListener(color, onColorClickListener);

        String colorName = color.getColorName();

        holder.viewBinding.colorItemNameTv.setText(colorName);
        holder.viewBinding.colorItemImageIv.setBackgroundResource(color.getPrimaryColor());
    }

    static class ColorHolder extends RecyclerView.ViewHolder {

        private ColorItemBinding viewBinding;

        public ColorHolder(View itemView) {
            super(itemView);
            viewBinding = DataBindingUtil.bind(itemView);
        }

        public void setClickListener(final Color color,
                                     final OnColorClickListener onColorClickListener) {
            viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onColorClickListener.onItemClick(color);
                }
            });
        }
    }
}
