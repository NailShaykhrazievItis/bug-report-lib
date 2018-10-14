package com.itis.android.githubapp.api.intercepors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Nail Shaykhraziev on 08.10.2018.
 */
class HeaderInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
        return chain.proceed(request)
    }
}
