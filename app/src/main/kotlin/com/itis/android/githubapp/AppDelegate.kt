package com.itis.android.githubapp

import android.app.Application
import com.itis.android.githubapp.di.initKodein
import com.squareup.picasso.Picasso
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class AppDelegate : Application(), KodeinAware {

    override val kodein: Kodein = initKodein(this)

    override fun onCreate() {
        super.onCreate()
        val picasso: Picasso by kodein.instance()
        Picasso.setSingletonInstance(picasso)
    }
}
