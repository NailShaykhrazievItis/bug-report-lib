package com.itis.testhelper.ui.bugreport

import com.itis.testhelper.model.bug.*
import com.itis.testhelper.repository.IssueRepository
import com.itis.testhelper.repository.StepsRepository
import com.itis.testhelper.repository.UserRepository
import com.itis.testhelper.ui.base.BasePresenter
import com.itis.testhelper.utils.STRING_EMPTY
import com.itis.testhelper.utils.STRING_USER_NOT_FOUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

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
                    severity: Severity, priority: Priority, frequency: Frequency,
                    environment: Environment, actual: String, expected: String) {
        if (userRepository.getAuthToken().isNotEmpty()) {
            launch(handler) {
                invokeSuspend {
                    val issue = async(Dispatchers.IO) {
                        val user = userRepository.getUser()
                        val repo = userRepository.getCurrentRepoName()
                        val login = user?.login ?: STRING_EMPTY
                        val userName = user?.run {
                            name += if (email.isNullOrEmpty()) STRING_EMPTY else "($email)"
                            name
                        } ?: STRING_USER_NOT_FOUND
                        val body = getIssueBody(title, summary, userName, precondition,
                                environment, severity, priority, frequency, actual, expected)
                        issueRepository.createIssue(login, repo, title, body)
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
            title: String, summary: String, reporter: String, precondition: String,
            environment: Environment, severity: Severity, priority: Priority, frequency: Frequency,
            actual: String, expected: String
    ): String = BugReport(title, summary, reporter, Date().toString(), precondition,
            environment, severity, priority, frequency,
            steps, actual, expected).toMarkdown()
}