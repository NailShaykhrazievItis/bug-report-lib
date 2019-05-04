package com.itis.testhelper.di.module

import com.itis.android.githubapp.utils.helpers.CoroutineCallAdapterFactory
import com.itis.testhelper.api.intercepors.ApiKeyInterceptor
import com.itis.testhelper.api.intercepors.HeaderInterceptor
import com.itis.testhelper.api.intercepors.LoggingInterceptor
import com.itis.testhelper.api.service.AuthService
import com.itis.testhelper.api.service.IssueService
import com.itis.testhelper.utils.API_ENDPOINT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun netModule() = Kodein.Module(name = "netModule") {

    bind<Interceptor>(tag = "logging") with singleton { LoggingInterceptor() }
    bind<Interceptor>(tag = "header") with singleton { HeaderInterceptor() }
    bind<Interceptor>(tag = "api") with singleton { ApiKeyInterceptor(instance()) }

    bind<OkHttpClient>() with singleton {
        provideOkHttpClient(instance(tag = "logging"), instance(tag = "header"), instance(tag = "api"))
    }
    bind<Retrofit>() with singleton { provideRetrofit(instance()) }

    bind<IssueService>() with singleton { instance<Retrofit>().create(IssueService::class.java) }
    bind<AuthService>() with singleton { instance<Retrofit>().create(AuthService::class.java) }
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
                .baseUrl(API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
