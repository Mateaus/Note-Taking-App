package com.example.mat.roomdb_mvvm.notes.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;
import com.example.mat.roomdb_mvvm.notes.note.ui.OnFavoriteClickListener;
import com.example.mat.roomdb_mvvm.notes.note.ui.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener onItemClickListener;
    private OnFavoriteClickListener onFavoriteClickListener;

    public NoteAdapter(OnItemClickListener listener, OnFavoriteClickListener onFavoriteClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = listener;
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getNoteId() == newItem.getNoteId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getNoteTag().equals(newItem.getNoteTag()) &&
                    oldItem.getNoteTitle().equals(newItem.getNoteTitle()) &&
                    oldItem.getNoteDescription().equals(newItem.getNoteDescription()) &&
                    oldItem.getNoteDate().equals(newItem.getNoteDate()) &&
                    oldItem.isNoteFavorite() == newItem.isNoteFavorite();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = getItem(position);
        holder.setClickListener(note, onItemClickListener);
        holder.setFavoriteClickListener(note, onFavoriteClickListener);

        String title = note.getNoteTitle();
        String description = note.getNoteDescription();
        String date = note.getNoteDate();
        Boolean favorite = note.isNoteFavorite();

        holder.titleTV.setText(title);
        holder.descriptionTV.setText(description);
        holder.dateTV.setText(date);

        if (favorite) {
            holder.favoriteIB.setImageResource(R.drawable.ic_star_full_black_24dp);
        } else {
            holder.favoriteIB.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public void updateNoteAt(int position, boolean val) {
        getItem(position).setNoteFavorite(val);
    }

    static class NoteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.titleTV)
        TextView titleTV;
        @BindView(R.id.descriptionTV)
        TextView descriptionTV;
        @BindView(R.id.favoriteIB)
        ImageButton favoriteIB;
        @BindView(R.id.dateTV)
        TextView dateTV;

        private View view;

        public NoteHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setClickListener(final Note note,
                                     final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(note);
                }
            });
        }

        public void setFavoriteClickListener(final Note note,
                                             final OnFavoriteClickListener listener) {
            favoriteIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFavoriteClick(note, getAdapterPosition());
                }
            });
        }
    }
}
