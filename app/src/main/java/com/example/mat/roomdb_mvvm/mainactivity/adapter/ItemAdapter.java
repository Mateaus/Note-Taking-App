package com.example.mat.roomdb_mvvm.mainactivity.adapter;

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
import com.example.mat.roomdb_mvvm.databinding.MenuItemBinding;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

public class ItemAdapter extends ListAdapter<DrawerMenuItem, ItemAdapter.ItemViewHolder> {

    private OnMenuItemClickListener onMenuItemClickListener;

    public ItemAdapter(OnMenuItemClickListener onMenuItemClickListener) {
        super(DIFF_CALLBACK);
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public static final DiffUtil.ItemCallback<DrawerMenuItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<DrawerMenuItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull DrawerMenuItem oldItem, @NonNull DrawerMenuItem newItem) {
            return oldItem.getMenuItemId() == newItem.getMenuItemId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DrawerMenuItem oldItem, @NonNull DrawerMenuItem newItem) {
            return oldItem.getMenuItemName().equals(newItem.getMenuItemName()) &&
                    oldItem.getMenuItemSize() == newItem.getMenuItemSize();
        }
    };

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DrawerMenuItem drawerMenuItem = getItem(position);
        holder.onItemClickListener(drawerMenuItem, onMenuItemClickListener);

        String menuItemName = drawerMenuItem.getMenuItemName();
        int menuItemSize = drawerMenuItem.getMenuItemSize();
        String menuIconName = drawerMenuItem.getMenuItemImage();

        Resources resources = holder.viewBinding.getRoot().getResources();
        Integer menuIcon = resources.getIdentifier(menuIconName, "drawable",
                holder.viewBinding.getRoot().getContext().getPackageName());

        holder.viewBinding.menuItemNameTv.setText(menuItemName);
        holder.viewBinding.menuItemSizeTv.setText(String.valueOf(menuItemSize));
        holder.viewBinding.menuItemIconIv.setImageResource(menuIcon);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private MenuItemBinding viewBinding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBinding = DataBindingUtil.bind(itemView);
        }

        public void onItemClickListener(final DrawerMenuItem drawerMenuItem,
                                        final OnMenuItemClickListener onMenuItemClickListener) {
            viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuItemClickListener.onMenuItemClick(drawerMenuItem);
                }
            });
        }
    }
}
