package com.itis.android.githubapp.ui.repodetails

import com.itis.android.githubapp.repository.RepoRepository
import com.itis.android.githubapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class RepoDetailsViewModel(private val repoRepository: RepoRepository) : BaseViewModel() {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


}
