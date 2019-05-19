package com.itis.testhelper.ui.settings.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itis.testhelper.R
import com.itis.testhelper.repository.RepositoryProvider
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(), SignInView {

    private lateinit var presenter: SignInPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        context?.also {
            presenter = SignInPresenter(this, RepositoryProvider.getUserRepository(it))
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.login)
        btn_sign_in_login.setOnClickListener {
            presenter.onLoginClick(
                    et_sign_in_login.text.toString(),
                    et_sign_in_pass.text.toString()
            )
        }
    }

    override fun successSignIn(token: String) {
        Toast.makeText(context, "SUCCESS AUTH: $token", Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        btn_sign_in_login.isEnabled = false
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        btn_sign_in_login.isEnabled = true
        progress.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(): SignInFragment = SignInFragment()
    }
}