package com.itis.android.githubapp.ui.repodetails

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.itis.android.githubapp.R
import com.itis.android.githubapp.ui.base.BaseViewPagerAdapter
import com.itis.android.githubapp.ui.repo.RepoInfoFragment
import kotlinx.android.synthetic.main.activity_repo_details.*

class RepoDetailsActivity : AppCompatActivity() {

    private var adapter: RepoViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        initToolbar()
        adapter = RepoViewPagerAdapter(supportFragmentManager)
        adapter?.setViewPager(vp_repo)
        tabs.setupWithViewPager(vp_repo)
    }

    private fun initToolbar() {
        setSupportActionBar(tb_repo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(EXTRA_REPO_NAME)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    inner class RepoViewPagerAdapter(fm: FragmentManager) : BaseViewPagerAdapter(fm) {

        fun setViewPager(viewPager: ViewPager) {
            val repoInfoFragment = RepoInfoFragment.newInstance()
            val repoInfoFragment2 = RepoInfoFragment.newInstance()
            adapter?.addFrag(repoInfoFragment, getString(R.string.info))
            adapter?.addFrag(repoInfoFragment2, "Branches")
            viewPager.offscreenPageLimit = 2
            viewPager.adapter = adapter
        }
    }

    companion object {

        const val EXTRA_REPO_NAME = "EXTRA_REPO_NAME"
    }
}
