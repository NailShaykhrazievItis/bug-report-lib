package com.itis.testhelper.ui.settings.setting

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.itis.testhelper.R
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.ui.settings.SettingsActivity
import com.itis.testhelper.utils.STRING_EMPTY
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(), SettingsView {

    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportFragmentManager?.apply {
            addOnBackStackChangedListener {
                if (backStackEntryCount == 0) {
                    presenter.updateUserState()
                    Log.e("Setting", "return")
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        context?.also {
            presenter = SettingsPresenter(this, RepositoryProvider.getUserRepository(it))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.settings)
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? AppCompatActivity)?.supportFragmentManager?.apply {
            removeOnBackStackChangedListener { }
        }
    }

    override fun setUserLogin(login: String) {
        tv_username.text = login
    }

    override fun setUserName(name: String) {
        tv_name.text = name
    }

    override fun setUserAvatar(avatarUrl: String) {
        iv_avatar.setImageURI(Uri.parse(avatarUrl))
    }

    override fun showUserProfile() {
        tv_sign_in.visibility = View.GONE
        group_profile.visibility = View.VISIBLE
    }

    override fun hideUserProfile() {
        tv_sign_in.visibility = View.VISIBLE
        group_profile.visibility = View.GONE
    }

    override fun showUserRepo() {
        cv_repo.visibility = View.VISIBLE
    }

    override fun hideUserRepo() {
        cv_repo.visibility = View.GONE
    }

    override fun setChooseRepoName(name: String) {
        tv_repo_name.text = name
    }

    override fun setEmptyRepoName() {
        tv_repo_name.text = getString(R.string.select_repository)
    }

    override fun showSignOutButton() {
        btn_sign_out.visibility = View.VISIBLE
    }

    override fun hideSignOutButton() {
        btn_sign_out.visibility = View.GONE
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) = Snackbar
            .make(container_profile, throwable.message ?: STRING_EMPTY, Snackbar.LENGTH_LONG)
            .show()

    override fun navigateToSignInScreen() {
        (activity as? SettingsActivity)?.openSignInFragment()
    }

    private fun initListeners() {
        tv_repo_name.setOnClickListener { presenter.onRepoChooseClick() }
        tv_sign_in.setOnClickListener { presenter.onSignInClick() }
        btn_sign_out.setOnClickListener { presenter.onSignOutClick() }
    }

    companion object {

        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}