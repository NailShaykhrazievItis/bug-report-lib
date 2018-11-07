package com.itis.android.githubapp.ui

import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itis.android.githubapp.R
import com.itis.android.githubapp.utils.buttonX
import com.itis.android.githubapp.utils.textInput
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityUi().setContentView(this)
    }

    fun hello(login: String, pass: String) {
        toast(login + pass)
    }

    class LoginActivityUi : AnkoComponent<LoginActivity> {

        override fun createView(ui: AnkoContext<LoginActivity>): View = with(ui) {
            verticalLayout {
                padding = dip(24)
                imageView(android.R.drawable.ic_menu_manage).lparams {
                    margin = dip(16)
                    gravity = Gravity.CENTER
                }

                val tiLogin = textInput {
                    editText {
                        hintResource = R.string.login
                        textSize = 16f
                    }
                }
                val tiPassword = textInput {
                    editText {
                        hintResource = R.string.password
                        inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                        textSize = 16f
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(16)
                }
                buttonX(theme = R.style.AppTheme) {
                    setText(R.string.login)
                    setTextColor(ContextCompat.getColor(ui.owner, R.color.icons))
                    onClick {
                        ui.owner.hello(tiLogin.editText?.text.toString(), tiPassword.editText?.text.toString())
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(16)
                }
            }
        }
    }
}
