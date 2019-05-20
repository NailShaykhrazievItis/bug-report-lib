package com.itis.testhelper.ui.bugreport

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

    init {
        launch {
            reportView.initSteps(withContext(Dispatchers.IO) {
                stepsRepository.getSteps()
            })
        }
    }

    fun onSendClick(title: String) {
//        launch(Dispatchers.IO) {
//            stepsRepository.clearSteps()
//        }
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

    fun stepRemoved(position: Int) = reportView.itemRemoved(position)

    private fun getIssueBody(): String {
        return STRING_EMPTY
    }
}