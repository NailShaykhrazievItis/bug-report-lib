package com.itis.testhelper.ui.settings.setting

import com.itis.testhelper.ui.base.BaseView

interface SettingsView : BaseView {

    fun setUserLogin(login: String)
    fun setUserName(name: String)
    fun setUserAvatar(avatarUrl: String)
    fun showUserProfile()
    fun hideUserProfile()
    fun showUserRepo()
    fun hideUserRepo()
    fun setChooseRepoName(name: String)
    fun setEmptyRepoName()
    fun showSignOutButton()
    fun hideSignOutButton()
    fun navigateToSignInScreen()
}