package com.itis.testhelper.di

import android.app.Application
import com.itis.testhelper.di.module.appModule
import com.itis.testhelper.di.module.domainModule
import com.itis.testhelper.di.module.netModule
import org.kodein.di.Kodein

fun initKodein(app: Application) =
        Kodein.lazy {
            import(appModule(app))
            import(netModule())
            import(domainModule())
        }
