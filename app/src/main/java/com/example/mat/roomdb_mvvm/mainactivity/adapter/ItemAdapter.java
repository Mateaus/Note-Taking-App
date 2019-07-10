package com.example.mat.roomdb_mvvm.mainactivity.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                .inflate(R.layout.activity_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DrawerMenuItem drawerMenuItem = getItem(position);
        holder.onItemClickListener(drawerMenuItem, onMenuItemClickListener);

        String menuItemName = drawerMenuItem.getMenuItemName();
        int menuItemSize = drawerMenuItem.getMenuItemSize();
        String menuIconName = drawerMenuItem.getMenuItemImage();

        Resources resources = holder.view.getResources();
        Integer menuIcon = resources.getIdentifier(menuIconName, "drawable",
                holder.view.getContext().getPackageName());

        holder.menuTV.setText(menuItemName);
        holder.menuSizeTV.setText(String.valueOf(menuItemSize));
        holder.menuIconIV.setImageResource(menuIcon);
    }

    public DrawerMenuItem getMenuItem(int position) {
        return getItem(position);
    }

    public List<DrawerMenuItem> getMenuList() {
        List<DrawerMenuItem> layoutMenuItems = new ArrayList<>(getItemCount());

        for(int i = 0; i < getItemCount(); i++) {
            layoutMenuItems.add(getItem(i));
        }

        return layoutMenuItems;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.menu_name_tv)
        TextView menuTV;

        @BindView(R.id.menu_size_tv)
        TextView menuSizeTV;

        @BindView(R.id.menu_icon_iv)
        ImageView menuIconIV;

        private View view;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void onItemClickListener(final DrawerMenuItem drawerMenuItem,
                                        final OnMenuItemClickListener onMenuItemClickListener) {
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuItemClickListener.onMenuItemClick(drawerMenuItem);
                }
            });
        }
    }
}
