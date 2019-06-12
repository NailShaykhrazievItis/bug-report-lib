package com.itis.testhelper.ui.bugreport

import com.itis.testhelper.model.bug.Step
import com.itis.testhelper.ui.base.BaseView

interface BugReportView : BaseView {

    fun initSteps(steps: List<Step>)

    fun itemRemoved(position: Int)

    fun itemAdded(step: Step)

    fun itemChanged(position: Int, step: Step)

    fun showSuccessCreateMessage(title: String)

    fun showChangeStepDialog(step: String)

    fun showAddStepDialog()

    fun navigateToSettings()
}
