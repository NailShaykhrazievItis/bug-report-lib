package com.itis.android.githubapp.di.module

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Created by Nail Shaykhraziev on 28.04.2018.
 */
fun appModule(app: Application) = Kodein.Module {

    bind<Context>() with singleton { app }
}
