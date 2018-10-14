package com.itis.android.githubapp.di

import android.app.Application
import com.itis.android.githubapp.di.module.appModule
import com.itis.android.githubapp.di.module.netModule
import com.itis.android.githubapp.di.module.picassoModule
import org.kodein.di.Kodein

lateinit var di: Kodein

fun initKodein(app: Application) {
    di = Kodein {
        import(appModule(app))
        import(netModule())
        import(picassoModule())
    }
}
