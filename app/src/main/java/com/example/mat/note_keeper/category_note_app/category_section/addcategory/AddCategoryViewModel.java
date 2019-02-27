package com.example.mat.note_keeper.category_note_app.category_section.addcategory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;

import static android.app.Activity.RESULT_OK;

public class AddCategoryViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> progress;
    private MutableLiveData<Integer> message;

    public AddCategoryViewModel(@NonNull Application application) {
        super(application);
        this.progress = new MutableLiveData<>();
        this.message = new MutableLiveData<>();
    }

    public void addCategory(Category category, DialogInterface dialog, Fragment fragment) {
        progress.setValue(true);
        String CategoryName = category.getCsubject();
        String CategoryDescription = category.getCdescription();

        if(CategoryName.trim().isEmpty() && CategoryDescription.trim().isEmpty()) {
            progress.setValue(false);
            dialog.dismiss();
        } else {
            Bundle extras = new Bundle();
            extras.putString(AddCategoryDialogFragment.EXTRA_NAME, CategoryName);
            extras.putString(AddCategoryDialogFragment.EXTRA_DESCRIPTION, CategoryDescription);

            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.setValue(R.string.category_created);
            progress.setValue(false);
            dialog.dismiss();
        }
    }

    public LiveData<Boolean> getProgress() {
        return this.progress;
    }

    public LiveData<Integer> getMessage() {
        return this.message;
    }
}
