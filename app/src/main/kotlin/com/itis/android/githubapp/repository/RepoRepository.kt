package com.itis.android.githubapp.repository

import com.itis.android.githubapp.api.service.RepoService
import com.itis.android.githubapp.utils.extensions.subscribeSingleOnIoObserveOnUi

class RepoRepository(val repoApi: RepoService) {

    fun getUserRepos() = repoApi.getUserRepos().subscribeSingleOnIoObserveOnUi()
}
