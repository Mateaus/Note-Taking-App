package com.example.mat.roomdb_mvvm.color.adapter;

import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.color.listener.OnColorClickListener;
import com.example.mat.roomdb_mvvm.databinding.ColorItemBinding;

public class ColorAdapter extends ListAdapter<Color, ColorAdapter.ColorHolder> {

    private Theme mTheme;
    private int selectedPosition;
    private OnColorClickListener onColorClickListener;

    public ColorAdapter(OnColorClickListener onColorClickListener) {
        super(DIFF_CALLBACK);
        this.onColorClickListener = onColorClickListener;
        this.selectedPosition = R.color.themePrimary;
        this.mTheme = new Theme(R.style.AppTheme, R.color.themePrimary, R.color.themePrimaryDark,
                R.color.themeAccent, false);
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
    public void onBindViewHolder(@NonNull final ColorHolder holder, final int position) {
        final Color color = getItem(position);

        String colorName = color.getColorName();
        colorName = colorName.replace(" Theme", "");

        Resources resources = holder.viewBinding.getRoot().getResources();

        if (mTheme.isDarkTheme()) {
            holder.viewBinding.colorItemNameTv.setTextColor(android.graphics.Color.WHITE);
            holder.viewBinding.colorItemRb.setButtonTintList(ColorStateList.valueOf(android.graphics.Color.WHITE));
        } else {
            holder.viewBinding.colorItemNameTv.setTextColor(resources.getColor(mTheme.getPrimaryColor()));
            holder.viewBinding.colorItemRb.setButtonTintList(ColorStateList.valueOf(resources.getColor(mTheme.getPrimaryColor())));
        }

        if (color.getPrimaryColor() == selectedPosition) {
            selectedPosition = position;
        }

        if (selectedPosition == position) {
            holder.viewBinding.colorItemRb.setChecked(true);

        } else {
            holder.viewBinding.colorItemRb.setChecked(false);
        }

        holder.viewBinding.colorItemRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition >= 0) {
                    notifyItemChanged(selectedPosition);
                }
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                onColorClickListener.onItemClick(color);
            }
        });


        holder.viewBinding.colorItemNameTv.setText(colorName);
    }

    public void setSelectedPosition(int colorId) {
        selectedPosition = colorId;
    }

    public void setTheme(Theme theme) {
        mTheme = theme;
    }

    static class ColorHolder extends RecyclerView.ViewHolder {

        private ColorItemBinding viewBinding;

        public ColorHolder(View itemView) {
            super(itemView);
            viewBinding = DataBindingUtil.bind(itemView);
        }
    }
}
