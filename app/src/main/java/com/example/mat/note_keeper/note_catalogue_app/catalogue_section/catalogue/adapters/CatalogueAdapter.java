package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.ui.OnCatalogueClickListener;
import com.example.mat.note_keeper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatalogueAdapter extends ListAdapter<Catalogue, CatalogueAdapter.CatalogueHolder> {

    private OnCatalogueClickListener onCatalogueClickListener;

    public CatalogueAdapter(OnCatalogueClickListener listener){
        super(DIFF_CALLBACK);
        this.onCatalogueClickListener = listener;
    }

    public static final DiffUtil.ItemCallback<Catalogue> DIFF_CALLBACK = new DiffUtil.ItemCallback<Catalogue>() {
        @Override
        public boolean areItemsTheSame(Catalogue oldItem, Catalogue newItem) {
            return oldItem.getC_id() == newItem.getC_id();
        }

        @Override
        public boolean areContentsTheSame(Catalogue oldItem, Catalogue newItem) {
            return oldItem.getCsubject().equals(newItem.getCsubject()) &&
                    oldItem.getCdescription().equals(newItem.getCdescription());
        }
    };

    @NonNull
    @Override
    public CatalogueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogue_item, parent, false);
        return new CatalogueHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueHolder holder, int position) {
        Catalogue catalogue = getItem(position);
        holder.setClickListener(catalogue, onCatalogueClickListener);
        holder.setLongClickListener(catalogue, onCatalogueClickListener);

        String catalogueName = catalogue.getCsubject();
        String catalogueDescription = catalogue.getCdescription();

        holder.subjectTV.setText(catalogueName);
        holder.descriptionTV.setText(catalogueDescription);
    }

    public Catalogue getCatalogueAt(int position) {
        return getItem(position);
    }

    static class CatalogueHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.subjectTV)
        TextView subjectTV;

        @BindView(R.id.descriptionTV)
        TextView descriptionTV;

        private View view;

        public CatalogueHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setClickListener(final Catalogue catalogue,
                                     final OnCatalogueClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(catalogue);
                }
            });
        }

        public void setLongClickListener(final Catalogue catalogue,
                                         final OnCatalogueClickListener listener) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(catalogue);
                    return false;
                }
            });
        }
    }

}
