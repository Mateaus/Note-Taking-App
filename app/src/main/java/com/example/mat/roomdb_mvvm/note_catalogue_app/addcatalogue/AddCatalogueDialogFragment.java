package com.example.mat.roomdb_mvvm.note_catalogue_app.addcatalogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.entity.Catalogue;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCatalogueDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

    public static final String EXTRA_NAME =
            "com.example.mat.roomdb_mvvm.EXTRA_NAME";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_DESCRIPTION";

    @BindView(R.id.nameET)
    EditText nameET;

    @BindView(R.id.descriptionET)
    EditText descriptionET;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private AddCatalogueViewModel addCatalogueViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Create Catalogue")
                .setPositiveButton("Create",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        View viewBody = inflater.inflate(R.layout.fragment_add_catalogue_dialog, null);
        ButterKnife.bind(this, viewBody);
        builder.setView(viewBody);

        this.addCatalogueViewModel = ViewModelProviders.of(this).get(AddCatalogueViewModel.class);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    @Override
    public void onShow(final DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();

        if(dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#757575"));
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.parseColor("#757575"));

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addCatalogueViewModel.addCatalogue(new Catalogue(nameET.getText().toString(),
                                    descriptionET.getText().toString()),
                            dialogInterface, getFragmentManager().findFragmentByTag("ADD_CATALOGUE"));
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        this.addCatalogueViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        this.addCatalogueViewModel.getProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                }
            }
        });

    }

    private void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
    }
}
