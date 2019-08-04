package com.example.mat.roomdb_mvvm.notes.note.adapter;

import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.databinding.NoteItemBinding;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;
import com.example.mat.roomdb_mvvm.notes.note.listener.OnFavoriteClickListener;
import com.example.mat.roomdb_mvvm.notes.note.listener.OnItemClickListener;

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

        holder.viewBinding.noteItemTitleTv.setText(title);
        holder.viewBinding.noteItemDescriptionTv.setText(description);
        holder.viewBinding.noteItemDateTv.setText(date);

        if (favorite) {
            holder.viewBinding.noteItemFavoriteIb.setImageResource(R.drawable.ic_star_full_black_24dp);
        } else {
            holder.viewBinding.noteItemFavoriteIb.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public void updateNoteAt(int position, boolean val) {
        getItem(position).setNoteFavorite(val);
    }

    public static class NoteHolder extends RecyclerView.ViewHolder {

        private NoteItemBinding viewBinding;

        public NoteHolder(View itemView) {
            super(itemView);
            viewBinding = DataBindingUtil.bind(itemView);
        }

        public void setClickListener(final Note note,
                                     final OnItemClickListener listener) {
            viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(note);
                }
            });
        }

        public void setFavoriteClickListener(final Note note,
                                             final OnFavoriteClickListener listener) {
            viewBinding.noteItemFavoriteIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFavoriteClick(note, getAdapterPosition());
                }
            });
        }

        private int fetchThemeColor(int attr) {
            TypedValue typedValue = new TypedValue();
            TypedArray typedArray = viewBinding.getRoot().getContext().obtainStyledAttributes(typedValue.data, new int[]{attr});
            int color = typedArray.getColor(0, 0);
            typedArray.recycle();

            return color;
        }
    }
}
