package com.itis.testhelper.ui.bugreport

import com.itis.testhelper.model.Step
import com.itis.testhelper.ui.base.BaseView

interface BugReportView : BaseView {

    fun initSteps(steps: List<Step>)

    fun showSuccessCreateMessage(title: String)

    fun navigateToAuth()
}
