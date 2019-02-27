package com.example.mat.note_keeper.category_note_app.category_section.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.categoryRepository = new CategoryRepository(application);
        this.allCategories = categoryRepository.getAllCategories();
    }

    public void insert(Category category) {
        categoryRepository.insert(category);
    }

    public void update(Category category) {
        categoryRepository.update(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
}
