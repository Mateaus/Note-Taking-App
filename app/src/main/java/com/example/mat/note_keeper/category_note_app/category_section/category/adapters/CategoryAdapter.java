package com.example.mat.note_keeper.category_note_app.category_section.category.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;
import com.example.mat.note_keeper.category_note_app.category_section.category.ui.OnCategoryClickListener;
import com.example.mat.note_keeper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryHolder> {

    private OnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(OnCategoryClickListener listener){
        super(DIFF_CALLBACK);
        this.onCategoryClickListener = listener;
    }

    public static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(Category oldItem, Category newItem) {
            return oldItem.getC_id() == newItem.getC_id();
        }

        @Override
        public boolean areContentsTheSame(Category oldItem, Category newItem) {
            return oldItem.getCsubject().equals(newItem.getCsubject()) &&
                    oldItem.getCdescription().equals(newItem.getCdescription());
        }
    };

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = getItem(position);
        holder.setClickListener(category, onCategoryClickListener);
        holder.setLongClickListener(category, onCategoryClickListener);

        String categoryName = category.getCsubject();
        String categoryDescription = category.getCdescription();

        holder.categoryTV.setText(categoryName);
        holder.descriptionTV.setText(categoryDescription);
    }

    public Category getCategoryAt(int position) {
        return getItem(position);
    }

    static class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.categoryTV)
        TextView categoryTV;

        @BindView(R.id.descriptionTV)
        TextView descriptionTV;

        private View view;

        public CategoryHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setClickListener(final Category category,
                                     final OnCategoryClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(category);
                }
            });
        }

        public void setLongClickListener(final Category category,
                                         final OnCategoryClickListener listener) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(category);
                    return false;
                }
            });
        }
    }

}
