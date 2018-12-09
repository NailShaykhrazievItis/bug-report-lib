package com.itis.android.githubapp.interactor

import com.itis.android.githubapp.model.Authorization
import com.itis.android.githubapp.repository.AuthRepository
import com.itis.android.githubapp.repository.PreferenceRepository
import io.reactivex.Single

class UserInteractor(private val authRepository: AuthRepository,
                     private val preferenceRepository: PreferenceRepository) {

    fun auth(login: String, password: String): Single<Authorization> =
            authRepository.auth(login, password)
                    .map {
                        preferenceRepository.saveAuthToken(it.token)
                        it
                    }
}
