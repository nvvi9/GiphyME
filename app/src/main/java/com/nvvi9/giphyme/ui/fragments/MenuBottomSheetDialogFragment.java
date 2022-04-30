package com.nvvi9.giphyme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.nvvi9.giphyme.R;

import java.util.function.Function;

public class MenuBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private final int menuRes;
    private final Function<MenuItem, Boolean> onNavigationItemSelected;

    public MenuBottomSheetDialogFragment(@MenuRes int menuRes, Function<MenuItem, Boolean> onNavigationItemSelected) {
        this.menuRes = menuRes;
        this.onNavigationItemSelected = onNavigationItemSelected;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_bottom_sheet_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.inflateMenu(menuRes);
        navigationView.setNavigationItemSelectedListener(item -> {
            boolean consumed = onNavigationItemSelected.apply(item);
            if (consumed) dismiss();
            return consumed;
        });
    }
}
