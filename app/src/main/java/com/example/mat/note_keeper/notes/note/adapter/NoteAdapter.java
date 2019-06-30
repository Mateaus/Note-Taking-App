package com.example.mat.note_keeper.notes.note.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.notes.note.entity.Note;
import com.example.mat.note_keeper.color.ui.OnColorClickListener;
import com.example.mat.note_keeper.notes.note.ui.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener onItemClickListener;
    private OnColorClickListener onColorClickListener;

    public NoteAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        //this.onColorClickListener = colorListener;
        this.onItemClickListener = listener;
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
                    oldItem.getNoteDate().equals(newItem.getNoteDate());
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
        //holder.changeRowContentColor(onColorClickListener);

        String title = note.getNoteTitle();
        String description = note.getNoteDescription();
        String date = note.getNoteDate();

        holder.titleTV.setText(title);
        holder.descriptionTV.setText(description);
        holder.dateTV.setText(date);
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    static class NoteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.titleTV)
        TextView titleTV;
        @BindView(R.id.descriptionTV)
        TextView descriptionTV;
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
/*
        public void changeRowContentColor(OnColorClickListener color) {
            cardView.setBackgroundColor(color.changeCardViewColor());
            titleTV.setTextColor(color.changeTitleColor());
            descriptionTV.setTextColor(color.changeDescriptionColor());
            dateTV.setTextColor(color.changeDateColor());
        }
*/
    }
}
