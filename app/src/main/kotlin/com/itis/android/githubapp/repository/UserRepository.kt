package com.itis.android.githubapp.repository

import com.itis.android.githubapp.api.service.UserService
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.utils.extensions.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single

class UserRepository(private val userService: UserService) {

    fun getUserByToken(): Single<User> =
            userService.getUserByToken().subscribeSingleOnIoObserveOnUi()

}
