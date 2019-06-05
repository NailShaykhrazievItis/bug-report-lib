package com.itis.testhelper.ui.bugreport

import com.itis.testhelper.model.Frequency
import com.itis.testhelper.model.Priority
import com.itis.testhelper.model.Severity
import com.itis.testhelper.model.Step
import com.itis.testhelper.repository.IssueRepository
import com.itis.testhelper.repository.StepsRepository
import com.itis.testhelper.repository.UserRepository
import com.itis.testhelper.ui.base.BasePresenter
import com.itis.testhelper.utils.STRING_EMPTY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BugReportPresenter(
        private var reportView: BugReportView,
        private val stepsRepository: StepsRepository,
        private val userRepository: UserRepository,
        private val issueRepository: IssueRepository
) : BasePresenter(reportView) {

    private var steps = ArrayList<Step>()
    private var changePosition = -1

    init {
        fetchSteps()
    }

    fun onSendClick(title: String, summary: String, precondition: String,
                    severity: Severity, priority: Priority, frequency: Frequency) {
        if (userRepository.getAuthToken().isNotEmpty()) {
            launch {
                invokeSuspend {
                    val issue = async(Dispatchers.IO) {
                        val user = userRepository.getUserName()
                        val repo = userRepository.getCurrentRepoName()
                        issueRepository.createIssue(user, repo, title, getIssueBody())
                    }
                    reportView.showSuccessCreateMessage(issue.await().title)
                }
            }
        } else {
            reportView.navigateToSettings()
        }
    }

    fun onSettingClick() = reportView.navigateToSettings()

    fun onItemClick(position: Int, step: Step) {
        reportView.showChangeStepDialog(step.name)
        changePosition = position
    }

    fun changeStep(name: String) {
        if (changePosition > -1) {
            steps[changePosition].name = name
            reportView.itemChanged(changePosition, steps[changePosition])
            saveSteps()
        }
    }

    fun stepRemoved(position: Int) {
        reportView.itemRemoved(position)
        steps.removeAt(position)
        stepsRepository.addSteps(steps)
    }

    fun onClearAllClick() {
        stepsRepository.clearSteps()
        fetchSteps()
    }

    fun onAddStepClick() = reportView.showAddStepDialog()

    fun addStep(name: String) {
        Step(name).also {
            reportView.itemAdded(it)
            steps.add(it)
            saveSteps()
        }
    }

    private fun saveSteps() {
        launch(Dispatchers.IO) {
            stepsRepository.addSteps(steps)
        }
    }

    private fun fetchSteps() {
        launch {
            invokeSuspend {
                val list = withContext(Dispatchers.IO) {
                    stepsRepository.getSteps()
                }
                steps = ArrayList(list)
                reportView.initSteps(list)
            }
        }
    }

    private fun getIssueBody(
//            name: String,
//            desc: String,
//            severity: Severity,
//            priority: Priority
    ): String {
        return STRING_EMPTY
    }
}