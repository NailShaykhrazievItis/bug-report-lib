package com.itis.android.githubapp.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.itis.android.githubapp.R
import com.itis.testhelper.ui.BackgroundActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*

class MainActivity : BackgroundActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.hostFragment)

        bnv_main.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.setTitle(when (destination.id) {
                R.id.navigation_profile -> R.string.menu_profile
                R.id.navigation_home -> R.string.menu_home
                R.id.navigation_search -> R.string.menu_search
                else -> R.string.app_name
            })
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.hostFragment).navigateUp()
}
