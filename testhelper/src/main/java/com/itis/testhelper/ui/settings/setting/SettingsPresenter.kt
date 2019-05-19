package com.itis.testhelper.ui.settings.setting

import com.itis.testhelper.repository.UserRepository
import com.itis.testhelper.ui.base.BasePresenter
import kotlinx.coroutines.launch

class SettingsPresenter(
        private val view: SettingsView,
        private val userRepository: UserRepository
) : BasePresenter(view) {

    fun initView() {
        launch {
            userRepository.getAuthToken()
        }
    }
}