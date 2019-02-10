package com.itis.android.githubapp.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android.githubapp.R
import com.itis.android.githubapp.ui.base.BaseFragment
import com.itis.android.githubapp.utils.extensions.provideViewModel

class RepoInfoFragment : BaseFragment() {

    override val viewModel: RepoInfoViewModel by provideViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_repo_info, container, false)

    override fun initObservers() {
    }

    companion object {

        fun newInstance(): RepoInfoFragment = RepoInfoFragment()
    }
}
