package com.itis.android.githubapp.di

import android.app.Application
import com.itis.android.githubapp.di.module.*
import org.kodein.di.Kodein

fun initKodein(app: Application) =
        Kodein.lazy {
            import(appModule(app))
            import(netModule())
            import(picassoModule())
            import(domainModule())
            import(viewModelModule())
        }
