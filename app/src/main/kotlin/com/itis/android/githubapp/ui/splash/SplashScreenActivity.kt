package com.itis.android.githubapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.itis.android.githubapp.R
import com.itis.android.githubapp.ui.MainActivity
import com.itis.android.githubapp.ui.auth.LoginActivity
import com.itis.android.githubapp.utils.extensions.provideViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: SplashScreenViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewModel.checkUserToken().observe(this, Observer {
            val target = if (it) MainActivity::class.java else LoginActivity::class.java
            startActivity(Intent(this@SplashScreenActivity, target))
            finish()
        })
    }
}
