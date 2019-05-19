package com.itis.testhelper.api

import com.itis.android.githubapp.utils.helpers.CoroutineCallAdapterFactory
import com.itis.testhelper.api.intercepors.ApiKeyInterceptor
import com.itis.testhelper.api.intercepors.HeaderInterceptor
import com.itis.testhelper.api.intercepors.LoggingInterceptor
import com.itis.testhelper.api.service.GithubService
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.utils.API_ENDPOINT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    val githubService: GithubService by lazy {
        retrofit.create(GithubService::class.java)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                .addInterceptor(HeaderInterceptor())
                .let { builder ->
                    RepositoryProvider.getUserRepository()?.also {
                        builder.addInterceptor(ApiKeyInterceptor(it))
                    }
                    builder.build()
                }
    }
}
