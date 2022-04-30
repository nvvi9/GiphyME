package com.nvvi9.giphyme.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.nvvi9.giphyme.R;
import com.nvvi9.giphyme.databinding.ActivityMainBinding;
import com.nvvi9.giphyme.utils.ViewUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            int destinationId = navDestination.getId();
            if (destinationId == R.id.trendingFragment || destinationId == R.id.favoriteFragment) {
                ViewUtils.showBottomNav(binding.bottomNav);
            } else {
                ViewUtils.hideBottomNav(binding.bottomNav);
            }
        });
    }

    public void startBrowserIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse(url));
        startActivity(intent);
    }
}
