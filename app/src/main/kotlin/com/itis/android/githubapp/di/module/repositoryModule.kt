package com.itis.android.githubapp.di.module

import com.itis.android.githubapp.repository.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun domainModule() = Kodein.Module(name = "domainModule") {

    bind<PreferenceRepository>() with singleton { PreferenceRepository(instance()) }

    bind<UserRepository>() with provider { UserRepository(instance()) }
    bind<AuthRepository>() with provider { AuthRepository(instance()) }
    bind<RepoRepository>() with provider { RepoRepository(instance()) }
    bind<SearchRepository>() with provider { SearchRepository(instance()) }
}
