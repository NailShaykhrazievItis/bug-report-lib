package com.itis.testhelper.ui.bugreport

import com.itis.testhelper.repository.IssueRepository
import com.itis.testhelper.repository.PreferenceRepository
import com.itis.testhelper.ui.base.BasePresenter
import com.itis.testhelper.utils.STRING_EMPTY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BugReportPresenter(
        private var reportView: BugReportView,
        private val preferenceRepository: PreferenceRepository,
        private val issueRepository: IssueRepository
) : BasePresenter(reportView) {

    init {
        launch {
            reportView.initSteps(withContext(Dispatchers.IO) {
                preferenceRepository.getSteps()
            })
        }
    }

    fun onSendClick(title: String) {
//        launch(Dispatchers.IO) {
//            preferenceRepository.clearSteps()
//        }
        if (preferenceRepository.getAuthToken().isNotEmpty()) {
            launch {
                invokeSuspend {
                    val issue = async(Dispatchers.IO) {
                        val user = preferenceRepository.getUserName()
                        val repo = preferenceRepository.getCurrentRepoName()
                        issueRepository.createIssue(user, repo, title, getIssueBody())
                    }
                    reportView.showSuccessCreateMessage(issue.await().title)
                }
            }
        } else {
            reportView.navigateToAuth()
        }
    }

    fun stepRemoved(position: Int) = reportView.itemRemoved(position)

    private fun getIssueBody(): String {
        return STRING_EMPTY
    }
}