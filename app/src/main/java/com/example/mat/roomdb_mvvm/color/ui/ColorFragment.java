package com.example.mat.roomdb_mvvm.color.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.ColorViewModel;
import com.example.mat.roomdb_mvvm.color.adapter.ColorAdapter;
import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.color.listener.OnColorClickListener;
import com.example.mat.roomdb_mvvm.databinding.FragmentColorBinding;
import com.example.mat.roomdb_mvvm.mainactivity.listener.StatusBarListener;
import com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity;

import java.util.List;


public class ColorFragment extends Fragment implements OnColorClickListener,
        CompoundButton.OnCheckedChangeListener {

    private ColorAdapter colorAdapter;
    private ColorViewModel colorViewModel;
    private StatusBarListener statusBarListener;

    private FragmentColorBinding viewBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        statusBarListener = (StatusBarListener) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_color, container, false);
        showBackButton(true);

        setUpToolBar();
        setUpCategoryAdapter();
        setUpRecyclerView();

        this.colorViewModel = ViewModelProviders.of(this.getActivity()).get(ColorViewModel.class);
        this.colorViewModel.getAllColors().observe(this, new Observer<List<Color>>() {
            @Override
            public void onChanged(@Nullable List<Color> colors) {
                if (colors != null && colors.size() != 0) {
                    colorAdapter.submitList(colors);
                }
            }
        });

        this.colorViewModel.getTheme().observe(this, new Observer<Theme>() {
            @Override
            public void onChanged(@Nullable Theme theme) {
                statusBarListener.setUpStatusBar(getResources()
                        .getColor(theme.getPrimaryDarkColor()));
            }
        });

        viewBinding.fragmentColorThemeSwitch.setOnCheckedChangeListener(this);

        return viewBinding.getRoot();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fragment_color_theme_switch:
                colorViewModel.updateThemeMode(isChecked);
                break;
        }
    }

    @Override
    public void onItemClick(Color color) {
        colorViewModel.updateTheme(new Theme(color.getColorStyle(), color.getPrimaryColor(),
                color.getPrimaryDarkColor(), color.getPrimaryLightColor(),
                viewBinding.fragmentColorThemeSwitch.isChecked()));
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.color_list);
    }

    private void setUpCategoryAdapter() {
        this.colorAdapter = new ColorAdapter(this);
    }

    private void setUpRecyclerView() {
        viewBinding.fragmentColorListRv.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        viewBinding.fragmentColorListRv.setAdapter(colorAdapter);
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showBackButton(enable);
    }
}
