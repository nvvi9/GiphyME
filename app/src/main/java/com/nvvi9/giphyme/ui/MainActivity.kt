package com.nvvi9.giphyme.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.ActivityMainBinding
import com.nvvi9.giphyme.utils.hideBottomNav
import com.nvvi9.giphyme.utils.showBottomNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNav, navController)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener { _: NavController?, navDestination: NavDestination, _: Bundle? ->
            val destinationId = navDestination.id
            if (destinationId == R.id.trendingFragment || destinationId == R.id.favoriteFragment) {
                binding.bottomNav.showBottomNav()
            } else {
                binding.bottomNav.hideBottomNav()
            }
        }
    }

    fun startBrowserIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse(url))
        startActivity(intent)
    }
}