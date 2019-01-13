package com.example.mat.roomdb_mvvm.color;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.example.mat.roomdb_mvvm.R;
import com.thebluealliance.spectrum.SpectrumPalette;

public class ColorPickerViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> colorPicked;

    public ColorPickerViewModel(@NonNull Application application) {
        super(application);
        colorPicked = new MutableLiveData<>();
    }

    public void findSelectedColor(final Resources resources, SpectrumPalette spectrumPalette) {

        spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                int colorSelected = 0;
                if (color == resources.getColor(R.color.black)) {
                    colorSelected = R.color.black;
                } else if (color == resources.getColor(R.color.grey)) {
                    colorSelected = R.color.grey;
                } else if (color == resources.getColor(R.color.white)) {
                    colorSelected = R.color.white;
                } else if (color == resources.getColor(R.color.darkred)) {
                    colorSelected = R.color.darkred;
                } else if (color == resources.getColor(R.color.red)) {
                    colorSelected = R.color.red;
                } else if (color == resources.getColor(R.color.lightred)) {
                    colorSelected = R.color.lightred;
                } else if (color == resources.getColor(R.color.darkblue)) {
                    colorSelected = R.color.darkblue;
                } else if (color == resources.getColor(R.color.blue)) {
                    colorSelected = R.color.blue;
                } else if (color == resources.getColor(R.color.lightblue)) {
                    colorSelected = R.color.lightblue;
                } else if (color == resources.getColor(R.color.darkgreen)) {
                    colorSelected = R.color.darkgreen;
                } else if (color == resources.getColor(R.color.green)) {
                    colorSelected = R.color.green;
                } else if (color == resources.getColor(R.color.lightgreen)) {
                    colorSelected = R.color.lightgreen;
                } else if (color == resources.getColor(R.color.darkyellow)) {
                    colorSelected = R.color.darkyellow;
                } else if (color == resources.getColor(R.color.yellow)) {
                    colorSelected = R.color.yellow;
                } else if (color == resources.getColor(R.color.lightyellow)) {
                    colorSelected = R.color.lightyellow;
                } else if (color == resources.getColor(R.color.darkbrown)) {
                    colorSelected = R.color.darkbrown;
                } else if (color == resources.getColor(R.color.brown)) {
                    colorSelected = R.color.brown;
                } else if (color == resources.getColor(R.color.lightbrown)) {
                    colorSelected = R.color.lightbrown;
                }

                if (colorSelected != 0) {
                    colorPicked.setValue(colorSelected);
                }
            }
        });
    }

    public LiveData<Integer> getTrigger() {
        return colorPicked;
    }


}
