package com.itis.testhelper.ui.settings.repo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.itis.testhelper.R
import com.itis.testhelper.model.Repository
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.utils.STRING_EMPTY
import kotlinx.android.synthetic.main.dialog_enter_repo.view.*
import kotlinx.android.synthetic.main.fragment_choose_repo.*

class ChooseRepoFragment : Fragment(), ChooseRepoView {

    private var adapter: ReposAdapter? = null

    private lateinit var presenter: ChooseRepoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_repo, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.choose_repository)
        context?.also {
            presenter = ChooseRepoPresenter(this, RepositoryProvider.getUserRepository(it))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showRepos(list: List<Repository>) {
        adapter?.submitList(list)
    }

    override fun showLoading() = pb_repos.show()

    override fun hideLoading() = pb_repos.hide()

    override fun showError(throwable: Throwable) = Snackbar
            .make(content_repos, throwable.message ?: STRING_EMPTY, Snackbar.LENGTH_LONG)
            .show()

    @SuppressLint("InflateParams")
    override fun showDialog() {
        context?.let {
            AlertDialog.Builder(it).apply {
                val dialogView = LayoutInflater.from(it).inflate(R.layout.dialog_enter_repo, null)
                setTitle("Provide repository name")
                setView(dialogView)
                setPositiveButton("OK") { _, _ ->
                    presenter.saveRepoName(dialogView.et_repo.text.toString())
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }

    override fun successReturn() {
        activity?.onBackPressed()
    }

    private fun initAdapter() {
        adapter = ReposAdapter { presenter.onRepoClick(it) }
        rv_repo.adapter = adapter
    }

    private fun initListeners() {
        fab_repo.setOnClickListener { presenter.onFabClick() }
    }

    companion object {

        fun newInstance(): ChooseRepoFragment = ChooseRepoFragment()
    }
}