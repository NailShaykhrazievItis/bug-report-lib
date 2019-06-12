package com.itis.testhelper.ui.settings.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.itis.testhelper.R
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.utils.extensions.afterTextChanged
import com.itis.testhelper.utils.extensions.isValidateEmpty
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
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun successSignIn(token: String) {
        Toast.makeText(context, "SUCCESS AUTH: $token", Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    override fun showLoading() {
        btn_sign_in_login.isEnabled = false
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        btn_sign_in_login?.isEnabled = true
        progress?.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
    }

    private fun initListeners() {
        btn_sign_in_login.setOnClickListener {
            if (et_sign_in_login.isValidateEmpty { setFieldEmptyError(ti_sign_in_login) } &&
                    et_sign_in_pass.isValidateEmpty { setFieldEmptyError(ti_sign_in_login) }) {
                presenter.onLoginClick(
                        et_sign_in_login.text.toString(),
                        et_sign_in_pass.text.toString(),
                        et_sign_in_note.text.toString()
                )
            }
        }
        initErrorClearListener(et_sign_in_login, ti_sign_in_login)
        initErrorClearListener(et_sign_in_pass, ti_sign_in_login)
    }

    private fun setFieldEmptyError(view: TextInputLayout) {
        view.error = getString(R.string.field_can_not_empty)
    }

    private fun initErrorClearListener(editText: EditText?, textInputLayout: TextInputLayout?) {
        editText?.afterTextChanged {
            textInputLayout?.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    companion object {

        fun newInstance(): SignInFragment = SignInFragment()
    }
}