package com.example.mat.note_keeper.color.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.note_keeper.mainactivity.ui.MainActivity;
import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.listener.StatusBarListener;
import com.example.mat.note_keeper.color.ColorViewModel;
import com.example.mat.note_keeper.color.adapter.ColorAdapter;
import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.color.entity.Theme;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ColorFragment extends Fragment implements OnColorClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ColorViewModel colorViewModel;
    private ColorAdapter colorAdapter;
    private StatusBarListener statusBarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        statusBarListener = (StatusBarListener) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color, container, false);
        ButterKnife.bind(this, v);
        showBackButton(true);

        setUpToolBar();

        setUpCategoryAdapter();
        setUpRecyclerView();

        this.colorViewModel = ViewModelProviders.of(this.getActivity()).get(ColorViewModel.class);
        this.colorViewModel.getAllColors().observe(this, new Observer<List<Color>>() {
            @Override
            public void onChanged(@Nullable List<Color> colors) {
                colorAdapter.submitList(colors);
            }
        });

        this.colorViewModel.getTheme().observe(this, new Observer<Theme>() {
            @Override
            public void onChanged(@Nullable Theme theme) {
                statusBarListener.setUpStatusBar(getResources()
                        .getColor(theme.getPrimaryDarkColor()));
            }
        });

        return v;
    }

    @Override
    public void onItemClick(Color color) {
        colorViewModel.updateTheme(new Theme(color.getColorStyle(), color.getPrimaryColor(),
                color.getPrimaryDarkColor(), color.getPrimaryLightColor()));
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.color_list);
    }

    private void setUpCategoryAdapter() {
        this.colorAdapter = new ColorAdapter(this);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(colorAdapter);
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.showBackButton(enable);
    }
}
