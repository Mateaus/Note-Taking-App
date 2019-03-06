package com.example.mat.note_keeper.category_note_app.category_section.category.ui;

import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;

public interface OnCategoryClickListener {
    void onItemClick(Category category);
    void onLongItemClick(Category category);
}
