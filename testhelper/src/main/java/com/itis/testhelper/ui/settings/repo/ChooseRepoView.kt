package com.itis.testhelper.ui.settings.repo

import com.itis.testhelper.model.Repository
import com.itis.testhelper.ui.base.BaseView

interface ChooseRepoView : BaseView {

    fun showRepos(list: List<Repository>)

    fun showDialog()

    fun successReturn()
}