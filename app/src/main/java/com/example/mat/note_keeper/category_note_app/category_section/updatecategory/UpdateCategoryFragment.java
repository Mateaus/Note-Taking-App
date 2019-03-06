package com.example.mat.note_keeper.category_note_app.category_section.updatecategory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateCategoryFragment extends Fragment {
    public static final String EXTRA_CID =
            "com.example.mat.roomdb_mvvm.EXTRA_CID";

    public static final String EXTRA_CSUBJECT =
            "com.example.mat.roomdb_mvvm.EXTRA_SCUBJECT";

    public static final String EXTRA_CDESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_CDESCRIPTION";

    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollView;
    @BindView(R.id.categoryET)
    EditText categoryET;
    @BindView(R.id.descriptionET)
    EditText descriptionET;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private UpdateCategoryViewModel updateCategoryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_category, container, false);
        ButterKnife.bind(this, v);

        this.updateCategoryViewModel = ViewModelProviders.of(this).get(UpdateCategoryViewModel.class);
        this.updateCategoryViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        setUpToolBar();
        setUpCategory();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        // TODO: Create a menute for category
        inflater.inflate(R.menu.update_note_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_note:
                updateCategory();
                return true;
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable menuHomeIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_close, null);
        menuHomeIcon = DrawableCompat.wrap(menuHomeIcon);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(menuHomeIcon);
        getActivity().setTitle(R.string.category_update);
    }

    private void setUpCategory() {
        String subject = getArguments().getString("csubject");
        String description = getArguments().getString("cdescription");

        categoryET.setText(subject);
        descriptionET.setText(description);
    }

    private void updateCategory() {
        this.updateCategoryViewModel.updateCategory(new Category(categoryET.getText().toString(),
                descriptionET.getText().toString()), this);
    }
}
