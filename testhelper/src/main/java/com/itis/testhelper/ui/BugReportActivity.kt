package com.itis.testhelper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.testhelper.R
import kotlinx.android.synthetic.main.activity_bug_report.*

class BugReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bug_report)
        initListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_change, R.anim.exit_to_bottom)
    }

    private fun initListeners() {
        iv_report_back.setOnClickListener { onBackPressed() }
    }
}