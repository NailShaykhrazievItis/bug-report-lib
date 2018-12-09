package com.itis.android.githubapp.di.module

import com.itis.android.githubapp.BuildConfig
import com.itis.android.githubapp.api.intercepors.ApiKeyInterceptor
import com.itis.android.githubapp.api.intercepors.HeaderInterceptor
import com.itis.android.githubapp.api.intercepors.LoggingInterceptor
import com.itis.android.githubapp.api.service.AuthService
import com.itis.android.githubapp.api.service.RepoService
import com.itis.android.githubapp.api.service.SearchService
import com.itis.android.githubapp.api.service.UserService
import com.itis.android.githubapp.repository.UserRepository
import com.itis.android.githubapp.utils.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun netModule() = Kodein.Module(name = "netModule") {

    bind<Interceptor>(tag = "logging") with singleton { LoggingInterceptor() }
    bind<Interceptor>(tag = "header") with singleton { HeaderInterceptor() }
    bind<Interceptor>(tag = "api") with singleton { ApiKeyInterceptor(instance()) }

    bind<OkHttpClient>() with singleton {
        provideOkHttpClient(instance(tag = "logging"), instance(tag = "header"), instance(tag = "api"))
    }
    bind<Retrofit>() with singleton { provideRetrofit(instance()) }

    bind<AuthService>() with singleton { instance<Retrofit>().create(AuthService::class.java) }
    bind<RepoService>() with singleton { instance<Retrofit>().create(RepoService::class.java) }
    bind<UserService>() with singleton { instance<Retrofit>().create(UserService::class.java) }
    bind<SearchService>() with singleton { instance<Retrofit>().create(SearchService::class.java) }
}

private fun provideOkHttpClient(loggingInterceptor: Interceptor,
                                headerInterceptor: Interceptor,
                                apiKeyInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(apiKeyInterceptor)
                .build()


private fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
