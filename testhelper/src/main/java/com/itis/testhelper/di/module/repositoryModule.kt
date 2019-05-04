package com.itis.testhelper.di.module

import com.itis.testhelper.repository.PreferenceRepository
import com.itis.testhelper.repository.PreferenceRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun domainModule() = Kodein.Module(name = "domainModule") {

    bind<PreferenceRepository>() with singleton { PreferenceRepositoryImpl(instance()) }

}
