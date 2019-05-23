package com.itis.testhelper.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itis.testhelper.R
import com.itis.testhelper.ui.settings.auth.SignInFragment
import com.itis.testhelper.ui.settings.repo.ChooseRepoFragment
import com.itis.testhelper.ui.settings.setting.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        openSettingsFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun openSettingsFragment() =
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance(), TAG_SETTING)
                    .commit()

    fun openSignInFragment() = forwardTo(SignInFragment.newInstance())

    fun openChooseRepoFragment() = forwardTo(ChooseRepoFragment.newInstance())

    private fun replaceTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.container, fragment)
                .addToBackStack(fragment.javaClass::getName.toString())
                .commit()
    }

    private fun forwardTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.container, fragment)
                .addToBackStack(fragment.javaClass::getName.toString())
                .commit()
    }

    companion object {
        private const val TAG_SETTING = "TAG_SETTING"
    }
}