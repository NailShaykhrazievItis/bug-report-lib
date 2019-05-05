package com.itis.testhelper.api

import com.itis.android.githubapp.utils.helpers.CoroutineCallAdapterFactory
import com.itis.testhelper.api.intercepors.ApiKeyInterceptor
import com.itis.testhelper.api.intercepors.HeaderInterceptor
import com.itis.testhelper.api.intercepors.LoggingInterceptor
import com.itis.testhelper.api.service.AuthService
import com.itis.testhelper.api.service.IssueService
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.utils.API_ENDPOINT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val issueService: IssueService by lazy {
        retrofit.create(IssueService::class.java)
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
                .addInterceptor(HeaderInterceptor()).let { builder ->
                    RepositoryProvider.getPreferenceRepository()?.also {
                        builder.addInterceptor(ApiKeyInterceptor(it))
                    }
                    builder.build()
                }
    }
}
