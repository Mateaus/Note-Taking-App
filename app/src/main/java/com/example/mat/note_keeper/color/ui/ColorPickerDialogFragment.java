package com.example.mat.note_keeper.color.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mat.note_keeper.R;
import com.thebluealliance.spectrum.SpectrumPalette;

import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class ColorPickerDialogFragment extends android.support.v4.app.DialogFragment implements DialogInterface.OnShowListener {

    public static final String EXTRA_COLOR =
            "com.example.mat.roomdb_mvvm.EXTRA_COLOR";

    private SpectrumPalette spectrumPalette;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        spectrumPalette = new SpectrumPalette(this.getContext());
        spectrumPalette.setColors(populateColors());
        spectrumPalette.setOutlineWidth(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Pick a color")
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        ButterKnife.bind(this, spectrumPalette);
        builder.setView(spectrumPalette);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#4b2c20"));
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.parseColor("#4b2c20"));


            spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
                @Override
                public void onColorSelected(int color) {
                    Bundle extras = new Bundle();
                    extras.putString(ColorPickerDialogFragment.EXTRA_COLOR, String.valueOf(color));
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(),
                            RESULT_OK,
                            new Intent().putExtras(extras)
                    );

                    dismiss();
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }

    private int[] populateColors() {
        int[] colors = {getResources().getColor(R.color.black),
                getResources().getColor(R.color.grey),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.darkred),
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.lightred),
                getResources().getColor(R.color.darkblue),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.lightblue),
                getResources().getColor(R.color.darkgreen),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.lightgreen),
                getResources().getColor(R.color.darkyellow),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.lightyellow),
                getResources().getColor(R.color.darkbrown),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.lightbrown)};

        return colors;
    }
}
