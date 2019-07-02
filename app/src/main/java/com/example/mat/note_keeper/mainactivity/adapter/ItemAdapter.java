package com.example.mat.note_keeper.mainactivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.note_keeper.mainactivity.model.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends ListAdapter<MenuItem, ItemAdapter.ItemViewHolder> {

    private OnMenuItemClickListener onMenuItemClickListener;

    public ItemAdapter(OnMenuItemClickListener onMenuItemClickListener) {
        super(DIFF_CALLBACK);
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public static final DiffUtil.ItemCallback<MenuItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<MenuItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull MenuItem oldItem, @NonNull MenuItem newItem) {
            return oldItem.getMenuItemId() == newItem.getMenuItemId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MenuItem oldItem, @NonNull MenuItem newItem) {
            return oldItem.getMenuItemName().equals(newItem.getMenuItemName()) &&
                    oldItem.getMenuItemSize() == newItem.getMenuItemSize();
        }
    };

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MenuItem menuItem = getItem(position);
        holder.onItemClickListener(menuItem, onMenuItemClickListener);

        String menuItemName = menuItem.getMenuItemName();
        int menuItemSize = menuItem.getMenuItemSize();

        holder.menuTV.setText(menuItemName);
        holder.menuSizeTV.setText(String.valueOf(menuItemSize));
    }

    public MenuItem getMenuItem(int position) {
        return getItem(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.menu_name_tv)
        TextView menuTV;

        @BindView(R.id.menu_size_tv)
        TextView menuSizeTV;

        private View view;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void onItemClickListener(final MenuItem menuItem,
                                        final OnMenuItemClickListener onMenuItemClickListener) {
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuItemClickListener.onMenuItemClick(menuItem);
                }
            });
        }
    }
}
