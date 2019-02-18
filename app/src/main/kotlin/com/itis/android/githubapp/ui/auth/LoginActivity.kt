package com.itis.android.githubapp.ui.auth

import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.itis.android.githubapp.R
import com.itis.android.githubapp.ui.MainActivity
import com.itis.android.githubapp.utils.anko.buttonX
import com.itis.android.githubapp.utils.anko.textInput
import com.itis.android.githubapp.utils.constants.NUMBER_SIXTEEN
import com.itis.android.githubapp.utils.constants.NUMBER_TWENTY_FOUR
import com.itis.android.githubapp.utils.extensions.provideViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: LoginViewModel by provideViewModel()
    private var loginUI: LoginActivityUi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginUI = LoginActivityUi()
        loginUI?.setContentView(this)
        initObservers()
    }

    private fun initObservers() {
        viewModel.result().observe(this, Observer {
            toast(it)
            startActivity<MainActivity>()
            finish()
        })
        viewModel.error().observe(this, Observer {
            toast("ERROR: $it")
        })
        viewModel.isLoading().observe(this, Observer {
            loginUI?.apply {
                progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    fun login(login: String, pass: String) = viewModel.auth(login, pass)

    class LoginActivityUi : AnkoComponent<LoginActivity> {

        lateinit var progress: ProgressBar

        @Suppress("LongMethod")
        override fun createView(ui: AnkoContext<LoginActivity>): View = with(ui) {
            frameLayout {
                verticalLayout {
                    padding = dip(NUMBER_TWENTY_FOUR)
                    imageView(R.mipmap.ic_launcher_foreground).lparams {
                        margin = dip(NUMBER_SIXTEEN)
                        gravity = Gravity.CENTER
                    }
                    val tiLogin = textInput {
                        editText {
                            hintResource = R.string.login
                            textSize = NUMBER_SIXTEEN.toFloat()
                            setText("NailShaykhrazievItis")
                        }
                    }
                    val tiPassword = textInput {
                        editText {
                            hintResource = R.string.password
                            inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                            textSize = NUMBER_SIXTEEN.toFloat()
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        topMargin = dip(NUMBER_SIXTEEN)
                    }
                    buttonX(theme = R.style.AppTheme) {
                        setText(R.string.login)
                        setTextColor(ContextCompat.getColor(ui.owner, R.color.icons))
                        onClick {
                            ui.owner.login(tiLogin.editText?.text.toString(), tiPassword.editText?.text.toString())
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        topMargin = dip(NUMBER_TWENTY_FOUR)
                    }
                }
                progress = progressBar {
                    visibility = View.GONE
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER
                }
            }
        }
    }
}
