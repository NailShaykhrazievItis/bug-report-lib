package com.itis.testhelper.ui.settings.setting

import com.itis.testhelper.repository.UserRepository
import com.itis.testhelper.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsPresenter(
        private val view: SettingsView,
        private val userRepository: UserRepository
) : BasePresenter(view) {

    init {
        updateUserState()
    }

    fun updateUserState() {
        launch {
            invokeSuspend {
                val token = withContext(Dispatchers.IO) { userRepository.getAuthToken() }
                if (token.isEmpty()) {
                    view.hideUserProfile()
                    view.hideUserRepo()
                    view.hideSignOutButton()
                } else {
                    (userRepository.getUser() ?: userRepository.getUserByTokenAsync()).apply {
                        view.setUserAvatar(avatarUrl)
                        view.setUserLogin(login)
                        view.setUserName(name)
                    }
                    userRepository.getCurrentRepoName().let {
                        if (it.isEmpty()) {
                            view.setEmptyRepoName()
                        } else {
                            view.setChooseRepoName(it)
                        }
                    }
                    view.showUserProfile()
                    view.showUserRepo()
                    view.showSignOutButton()
                }
            }
        }
    }

    fun onSignOutClick() {
        launch(Dispatchers.IO) {
            userRepository.removeUser()
            userRepository.removeToken()
            userRepository.removeRepoName()
        }
    }

    fun onSignInClick() = view.navigateToSignInScreen()

    fun onRepoChooseClick() = view.navigateToRepoChooseScreen()

}