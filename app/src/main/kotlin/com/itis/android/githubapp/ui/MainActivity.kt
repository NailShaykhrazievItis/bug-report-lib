package com.itis.android.githubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.itis.android.githubapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.hostFragment)

        bnv_main.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.hostFragment).navigateUp()
}
