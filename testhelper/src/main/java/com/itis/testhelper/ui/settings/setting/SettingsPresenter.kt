package com.itis.testhelper.ui.settings.setting

import com.itis.testhelper.repository.UserRepository
import com.itis.testhelper.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                val token = userRepository.getAuthToken()
                if (token.isEmpty()) {
                    view.apply {
                        hideUserProfile()
                        hideUserRepo()
                        hideSignOutButton()
                    }
                } else {
                    (userRepository.getUser() ?: userRepository.getUserByTokenAsync()).let {
                        view.apply {
                            setUserAvatar(it.avatarUrl)
                            setUserLogin(it.login)
                            setUserName(it.name)
                        }
                    }
                    userRepository.getCurrentRepoName().let {
                        if (it.isEmpty()) {
                            view.setEmptyRepoName()
                        } else {
                            view.setChooseRepoName(it)
                        }
                    }
                    view.apply {
                        showUserProfile()
                        showUserRepo()
                        showSignOutButton()
                    }
                }
            }
        }
    }

    fun onSignOutClick() {
        launch(Dispatchers.IO) {
            userRepository.apply {
                removeUser()
                removeToken()
                removeRepoName()
            }
        }
    }

    fun onSignInClick() = view.navigateToSignInScreen()

    fun onRepoChooseClick() = view.navigateToRepoChooseScreen()

}