package com.example.mat.roomdb_mvvm.mainactivity.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.mainactivity.MainViewModel;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.roomdb_mvvm.notes.note.ui.NoteListFragment;

public class TagDeleteDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private MainViewModel mainViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View customTitleView = LayoutInflater.from(getContext()).inflate(R.layout.tag_custom_title, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setCustomTitle(customTitleView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_delete_tag_warning, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }


    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        TextView customTitleTV = (TextView) dialog.findViewById(R.id.dialog_custom_title_TV);
        customTitleTV.setTextColor(fetchThemeColor(R.attr.colorPrimaryDark));
        customTitleTV.setText("Delete Tag");

        Button acceptButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        acceptButton.setTextColor(getResources().getColor(R.color.red));
        Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        cancelButton.setTextColor(getResources().getColor(R.color.blue));

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = getArguments().getString("menu_tag_image");
                String name = getArguments().getString("menu_tag_name");
                Integer id = Integer.valueOf(getArguments().getString("menu_tag_id"));
                Integer size = Integer.valueOf(getArguments().getString("menu_tag_size"));
                mainViewModel.deleteMenuItem(new DrawerLayoutMenuItem(id, name, size, image));
                loadAllNotesFragment();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int fetchThemeColor(int attr) {
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = getContext().obtainStyledAttributes(typedValue.data, new int[]{attr});
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();

        return color;
    }

    private void loadAllNotesFragment() {
        NoteListFragment noteListFragment = new NoteListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_id", "1");
        bundle.putString("menu_name", "All Notes");
        bundle.putString("menu_icon", "note_icon");
        noteListFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.activity_main_fl, noteListFragment).commit();
    }
}
