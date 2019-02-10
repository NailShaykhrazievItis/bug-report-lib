package com.itis.android.githubapp.di.module

import com.itis.android.githubapp.repository.*
import com.itis.android.githubapp.repository.impl.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun domainModule() = Kodein.Module(name = "domainModule") {

    bind<PreferenceRepository>() with singleton { PreferenceRepositoryImpl(instance()) }

    bind<UserRepository>() with provider { UserRepositoryImpl(instance()) }
    bind<AuthRepository>() with provider { AuthRepositoryImpl(instance()) }
    bind<RepoRepository>() with provider { RepoRepositoryImpl(instance()) }
    bind<SearchRepository>() with provider { SearchRepositoryImpl(instance()) }
}
