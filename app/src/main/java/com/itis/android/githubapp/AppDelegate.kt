package com.itis.android.githubapp

import android.app.Application
import com.itis.android.githubapp.di.di
import com.itis.android.githubapp.di.initKodein
import com.squareup.picasso.Picasso
import org.kodein.di.generic.instance

/**
 * Created by Nail Shaykhraziev on 27.04.2018.
 */
class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        initKodein(this)
        val picasso: Picasso by di.instance()
        Picasso.setSingletonInstance(picasso)
    }
}
