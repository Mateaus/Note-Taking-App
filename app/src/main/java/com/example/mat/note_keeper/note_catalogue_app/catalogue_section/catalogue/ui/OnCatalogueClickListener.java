package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.ui;

import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;

public interface OnCatalogueClickListener {
    void onItemClick(Catalogue catalogue);
    void onLongItemClick(Catalogue catalogue);
}
