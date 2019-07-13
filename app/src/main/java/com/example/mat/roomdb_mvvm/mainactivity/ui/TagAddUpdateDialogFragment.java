package com.example.mat.roomdb_mvvm.mainactivity.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.databinding.FragmentDialogAddUpdateTagBinding;
import com.example.mat.roomdb_mvvm.mainactivity.MainViewModel;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

import static com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity.ADD_TAG;
import static com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity.EDIT_TAG;

public class TagAddUpdateDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private MainViewModel mainViewModel;
    private FragmentDialogAddUpdateTagBinding viewBinding;

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

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_add_update_tag, null);
        builder.setView(view);
        viewBinding = DataBindingUtil.bind(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();

        TextView customTitleTV = (TextView) dialog.findViewById(R.id.dialog_custom_title_TV);
        customTitleTV.setTextColor(getResources().getColor(R.color.themePrimary));

        if (getTag().equals(ADD_TAG)) {
            customTitleTV.setText("New Tag");
        } else if (getTag().equals(EDIT_TAG)) {
            viewBinding.dialogAddUpdateFragmentEt.setText(getArguments().getString("menu_tag_name"));
            customTitleTV.setText("Edit Tag");
        }

        Button acceptButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        acceptButton.setTextColor(fetchThemeColor(R.attr.colorAccent));
        acceptButton.setTypeface(null, Typeface.BOLD);
        Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        cancelButton.setTextColor(fetchThemeColor(R.attr.colorAccent));
        cancelButton.setTypeface(null, Typeface.BOLD);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewBinding.dialogAddUpdateFragmentEt.getText().toString().isEmpty()) {
                    dismiss();
                } else {
                    if (getTag().equals(ADD_TAG)) {
                        mainViewModel.insertTagMenuItem(
                                new DrawerMenuItem(viewBinding.dialogAddUpdateFragmentEt.getText().toString(),
                                        0, "tag_border_icon"));

                    } else if (getTag().equals(EDIT_TAG)) {
                        String image = getArguments().getString("menu_tag_image");
                        String name = getArguments().getString("menu_tag_name");
                        Integer id = Integer.valueOf(getArguments().getString("menu_tag_id"));
                        Integer size = Integer.valueOf(getArguments().getString("menu_tag_size"));
                        if (viewBinding.dialogAddUpdateFragmentEt.getText().toString().equals(name)) {
                            dismiss();
                        } else {
                            mainViewModel.updateMenuItem(new DrawerMenuItem(id,
                                    viewBinding.dialogAddUpdateFragmentEt.getText().toString(), size, image));
                        }
                    }
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
