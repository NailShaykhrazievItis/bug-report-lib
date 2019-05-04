package com.itis.testhelper.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun appModule(app: Application) = Kodein.Module(name = "appModule") {

    bind<Context>() with singleton { app }
    bind<SharedPreferences>() with singleton { PreferenceManager.getDefaultSharedPreferences(instance()) }
}
