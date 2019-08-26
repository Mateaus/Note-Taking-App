package com.example.mat.roomdb_mvvm.color.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.ColorViewModel;
import com.example.mat.roomdb_mvvm.color.adapter.ColorAdapter;
import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.color.listener.OnColorClickListener;
import com.example.mat.roomdb_mvvm.databinding.FragmentColorBinding;
import com.example.mat.roomdb_mvvm.fragmentbasecallback.BaseFragmentListener;

import java.util.List;


public class ColorFragment extends Fragment implements OnColorClickListener,
        CompoundButton.OnCheckedChangeListener {

    private boolean isDarkTheme;

    private Context mContext;
    private ColorAdapter colorAdapter;
    private ColorViewModel colorViewModel;
    private ColorFragmentListener listener;

    private FragmentColorBinding viewBinding;

    public interface ColorFragmentListener extends BaseFragmentListener {
        void setUpStatusBarColor(int colorId);

        void setDarkTheme(boolean isDarkTheme);
    }

    public static ColorFragment newInstance(boolean isDarkTheme) {
        ColorFragment colorFragment = new ColorFragment();

        Bundle colorBundle = new Bundle();
        colorBundle.putBoolean("switch_boolean", isDarkTheme);
        colorFragment.setArguments(colorBundle);

        return colorFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (ColorFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ColorFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDarkTheme = getArguments() != null && getArguments().getBoolean("switch_boolean");
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_color, container, false);

        setUI();

        return viewBinding.getRoot();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fragment_color_theme_switch:
                colorViewModel.updateThemeMode(isChecked);
                listener.setDarkTheme(isChecked);
                break;
        }
    }

    @Override
    public void onItemClick(Color color) {
        colorViewModel.updateTheme(new Theme(color.getColorStyle(), color.getPrimaryColor(),
                color.getPrimaryDarkColor(), color.getPrimaryLightColor(),
                viewBinding.fragmentColorThemeSwitch.isChecked()));
    }

    private void setUI() {
        listener.setBackButtonVisible(true);
        listener.setToolbarTitle(getString(R.string.color_list));
        viewBinding.fragmentColorThemeSwitch.setChecked(isDarkTheme);

        setUpToolBar();
        setUpCategoryAdapter();
        setUpRecyclerView();
        setUpLiveData();

        viewBinding.fragmentColorThemeSwitch.setOnCheckedChangeListener(this);
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
    }

    private void setUpCategoryAdapter() {
        this.colorAdapter = new ColorAdapter(this);
    }

    private void setUpRecyclerView() {
        viewBinding.fragmentColorListRv.setLayoutManager(new LinearLayoutManager(mContext));
        viewBinding.fragmentColorListRv.setAdapter(colorAdapter);
    }

    private void setUpLiveData() {
        this.colorViewModel = ViewModelProviders.of(this).get(ColorViewModel.class);
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
                if (theme != null) {
                    listener.setUpStatusBarColor(getResources()
                            .getColor(theme.getPrimaryDarkColor()));
                    colorAdapter.setSelectedPosition(theme.getPrimaryColor());
                    colorAdapter.setTheme(theme);
                    colorAdapter.notifyDataSetChanged();
                    DrawableCompat.setTintList(DrawableCompat.wrap(viewBinding.fragmentColorThemeSwitch.getThumbDrawable()),
                            new ColorStateList(new int[][]{new int[]{-android.R.attr.state_checked},
                                    new int[]{android.R.attr.state_checked}},
                                    new int[]{getResources().getColor(theme.getPrimaryColor()),
                                            getResources().getColor(theme.getPrimaryDarkColor())}));

                    if (theme.isDarkTheme()) {
                        viewBinding.fragmentColorBottomV.setBackgroundColor(android.graphics.Color.WHITE);
                        viewBinding.fragmentColorDarkThemeTv.setTextColor(android.graphics.Color.WHITE);
                        viewBinding.fragmentColorThemeTv.setTextColor(android.graphics.Color.WHITE);
                    } else {
                        viewBinding.fragmentColorBottomV.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        viewBinding.fragmentColorDarkThemeTv.setTextColor(android.graphics.Color.BLACK);
                        viewBinding.fragmentColorThemeTv.setTextColor(android.graphics.Color.BLACK);
                    }
                }
            }
        });
    }

    private int fetchThemeColor(int attr) {
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = viewBinding.getRoot()
                .getContext().obtainStyledAttributes(typedValue.data, new int[]{attr});
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();

        return color;
    }
}
