package com.nvvi9.giphyme.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.nvvi9.giphyme.R

class MenuBottomSheetDialogFragment(
    @MenuRes private val menuRes: Int,
    private val onNavigationItemSelected: (MenuItem) -> Boolean
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.menu_bottom_sheet_dialog_layout, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view)
        navigationView.inflateMenu(menuRes)
        navigationView.setNavigationItemSelectedListener {
            val consumed = onNavigationItemSelected(it)
            if (consumed) dismiss()
            consumed
        }
    }
}