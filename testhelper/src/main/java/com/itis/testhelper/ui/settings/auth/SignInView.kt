package com.itis.testhelper.ui.settings.auth

import com.itis.testhelper.ui.base.BaseView

interface SignInView : BaseView {

    fun successSignIn(token: String)
}