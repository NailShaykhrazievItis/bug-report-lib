package com.itis.android.githubapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.itis.android.githubapp.R
import com.itis.android.githubapp.ui.adapters.ReposAdapter
import com.itis.android.githubapp.ui.auth.LoginActivity
import com.itis.android.githubapp.ui.base.BaseFragment
import com.itis.android.githubapp.ui.repodetails.RepoDetailsActivity
import com.itis.android.githubapp.ui.repodetails.RepoDetailsActivity.Companion.EXTRA_REPO_NAME
import com.itis.android.githubapp.utils.extensions.provideViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.KodeinAware

class ProfileFragment : BaseFragment(), KodeinAware {

    override val viewModel: ProfileViewModel by provideViewModel()

    private var adapter: ReposAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
    }

    override fun initObservers() {
        provideLoadingObservers(progress)
        provideErrorObservers(container_profile)
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            tv_username.text = it.login
            tv_name.text = it.name
            tv_bio.text = it.bio
            tv_company.text = it.company
            tv_location.text = it.location
            Glide.with(this)
                    .load(it.avatarUrl)
                    .into(iv_avatar)
        })
        viewModel.getRepos().observe(viewLifecycleOwner, Observer {
            adapter?.submitList(it)
        })
        viewModel.navigateToAuth.observe(viewLifecycleOwner, Observer {
            activity?.run {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun initAdapter() {
        adapter = ReposAdapter { repo ->
            activity?.let {
                val intent = Intent(it, RepoDetailsActivity::class.java)
                intent.putExtra(EXTRA_REPO_NAME, repo.name)
                startActivity(intent)
            }
        }
        rv_repo.adapter = adapter
    }

    private fun initListeners() {
        btn_sign_out.setOnClickListener { viewModel.signOutClick() }
        tv_followers.setOnClickListener { }
        tv_following.setOnClickListener { }
        tv_feed.setOnClickListener { }
        tv_stars.setOnClickListener { }
    }
}
