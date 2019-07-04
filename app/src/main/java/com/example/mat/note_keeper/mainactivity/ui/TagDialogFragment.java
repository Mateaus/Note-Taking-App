package com.example.mat.note_keeper.mainactivity.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.MainViewModel;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

    @BindView(R.id.tagET)
    EditText tagET;

    private TagCategory tagCategory;
    private MainViewModel mainViewModel;

    public TagDialogFragment(TagCategory tagCategory) {
        this.tagCategory = tagCategory;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View customTitleView = LayoutInflater.from(getContext()).inflate(R.layout.add_tag_custom_title, null);
        TextView customTitleTV = (TextView) customTitleView.findViewById(R.id.dialog_custom_title_TV);
        customTitleTV.setText("New Tag");

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

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_add_tag, null);
        ButterKnife.bind(this, view);
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

        Button acceptButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tagET.getText().toString().isEmpty()) {
                    dismiss();
                } else {
                    tagCategory.getItems().add(0, new Tag(tagET.getText().toString(), 0));
                    mainViewModel.updateTagCategory(tagCategory);
                    //mainViewModel.insertTag(new TagCategory("Tags", new ArrayList<>()));
                    dismiss();
                }
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
}
