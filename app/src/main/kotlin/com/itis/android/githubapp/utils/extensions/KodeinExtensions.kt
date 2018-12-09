package com.itis.android.githubapp.utils.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein.Builder
import org.kodein.di.Kodein.Builder.TypeBinder
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

inline fun <reified VM : ViewModel, T> T.provideViewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity {
    return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java) }
}

inline fun <reified T : ViewModel> Builder.bindViewModel(overrides: Boolean? = null): TypeBinder<T> {
    return bind<T>(T::class.java.simpleName, overrides)
}
