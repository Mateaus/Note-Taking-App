package com.example.mat.note_keeper.notes.note.observable;

import java.io.Serializable;
import java.util.Observable;

public class ObservableColorInteger extends Observable implements Serializable {

    private int colorValue;

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
        this.setChanged();
        this.notifyObservers();
    }

}
