package com.itis.android.githubapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.itis.android.githubapp.ui.auth.LoginViewModel
import com.itis.android.githubapp.ui.splash.SplashScreenViewModel
import com.itis.android.githubapp.utils.extensions.bindViewModel
import com.itis.android.githubapp.utils.vm.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun viewModelModule() = Kodein.Module(name = "viewModelModule") {

    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(dkodein) }

    bindViewModel<LoginViewModel>() with provider { LoginViewModel(instance(), instance()) }
    bindViewModel<SplashScreenViewModel>() with provider { SplashScreenViewModel(instance()) }
}
