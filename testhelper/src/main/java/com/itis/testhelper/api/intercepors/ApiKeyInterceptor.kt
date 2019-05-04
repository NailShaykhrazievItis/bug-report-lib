package com.itis.testhelper.api.intercepors

import com.itis.testhelper.repository.PreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor(private val preferenceRepository: PreferenceRepository) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferenceRepository.getAuthToken()
        if (token.isEmpty() || chain.request().url().encodedPath().contains("authorizations")) {
            return chain.proceed(chain.request())
        }
        val request = chain.request().newBuilder()
                .addHeader("Authorization", String.format("%s %s", "token", token))
                .build()
        return chain.proceed(request)
    }
}
