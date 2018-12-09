package com.itis.android.githubapp.interactor

import com.itis.android.githubapp.model.Authorization
import com.itis.android.githubapp.repository.PreferenceRepository
import com.itis.android.githubapp.repository.UserRepository
import io.reactivex.Single

class UserInteractor(private val userRepository: UserRepository,
                     private val preferenceRepository: PreferenceRepository) {

    fun auth(login: String, password: String): Single<Authorization> =
            userRepository.auth(login, password)
                    .map {
                        preferenceRepository.saveAuthToken(it.token)
                        it
                    }
}
