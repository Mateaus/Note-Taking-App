package com.example.mat.note_keeper.mainactivity.adapter;

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

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.note_keeper.mainactivity.model.DrawerLayoutMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends ListAdapter<DrawerLayoutMenuItem, ItemAdapter.ItemViewHolder> {

    private OnMenuItemClickListener onMenuItemClickListener;

    public ItemAdapter(OnMenuItemClickListener onMenuItemClickListener) {
        super(DIFF_CALLBACK);
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public static final DiffUtil.ItemCallback<DrawerLayoutMenuItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<DrawerLayoutMenuItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull DrawerLayoutMenuItem oldItem, @NonNull DrawerLayoutMenuItem newItem) {
            return oldItem.getMenuItemId() == newItem.getMenuItemId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DrawerLayoutMenuItem oldItem, @NonNull DrawerLayoutMenuItem newItem) {
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
        DrawerLayoutMenuItem drawerLayoutMenuItem = getItem(position);
        holder.onItemClickListener(drawerLayoutMenuItem, onMenuItemClickListener);

        String menuItemName = drawerLayoutMenuItem.getMenuItemName();
        int menuItemSize = drawerLayoutMenuItem.getMenuItemSize();
        String menuIconName = drawerLayoutMenuItem.getMenuItemImage();

        Resources resources = holder.view.getResources();
        Integer menuIcon = resources.getIdentifier(menuIconName, "drawable",
                holder.view.getContext().getPackageName());

        holder.menuTV.setText(menuItemName);
        holder.menuSizeTV.setText(String.valueOf(menuItemSize));
        holder.menuIconIV.setImageResource(menuIcon);
    }

    public DrawerLayoutMenuItem getMenuItem(int position) {
        return getItem(position);
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

        public void onItemClickListener(final DrawerLayoutMenuItem drawerLayoutMenuItem,
                                        final OnMenuItemClickListener onMenuItemClickListener) {
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuItemClickListener.onMenuItemClick(drawerLayoutMenuItem);
                }
            });
        }
    }
}
