package com.itis.android.githubapp.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itis.android.githubapp.R

class RepoInfoFragment : Fragment() {

    companion object {

        fun newInstance(): RepoInfoFragment = RepoInfoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_repo_info, container, false)

}
