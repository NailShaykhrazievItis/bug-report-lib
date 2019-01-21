package com.itis.android.githubapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.itis.android.githubapp.R
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.ui.adapters.ReposAdapter
import com.itis.android.githubapp.ui.repodetails.RepoDetailsActivity
import com.itis.android.githubapp.ui.repodetails.RepoDetailsActivity.Companion.EXTRA_REPO_NAME
import com.itis.android.githubapp.utils.extensions.provideViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: ProfileViewModel by provideViewModel()
    private var adapter: ReposAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
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

    private fun initObservers() {
        viewModel.isLoading().observe(viewLifecycleOwner, Observer {
            progress.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Outcome.Success -> {
                    tv_username.text = it.data.login
                    tv_name.text = it.data.name
                    tv_bio.text = it.data.bio
                    tv_company.text = it.data.company
                    tv_location.text = it.data.location
                    Glide.with(this)
                            .load(it.data.avatarUrl)
                            .into(iv_avatar)
                }
                is Outcome.Error -> {

                }
            }
        })
        viewModel.getRepos().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Outcome.Success -> {
                    adapter?.submitList(it.data)
                }
                is Outcome.Error -> {

                }
            }
        })
    }
}
