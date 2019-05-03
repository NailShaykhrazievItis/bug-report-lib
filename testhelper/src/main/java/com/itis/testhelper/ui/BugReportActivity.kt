package com.itis.testhelper.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.itis.testhelper.R
import com.itis.testhelper.model.Priority
import com.itis.testhelper.model.Severity
import kotlinx.android.synthetic.main.activity_bug_report.*

class BugReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bug_report)
        initListeners()
        initPriorityAdapter()
        initSeverityAdapter()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_change, R.anim.exit_to_bottom)
    }

    private fun initListeners() {
        iv_report_back.setOnClickListener { onBackPressed() }
    }

    private fun initSeverityAdapter() {
        val severity = arrayOf(Severity.BLOCKER, Severity.CRITICAL, Severity.MAJOR, Severity.MINOR, Severity.TRIVIAL)
        val adapterSeverity = ArrayAdapter<Severity>(this, android.R.layout.simple_spinner_item, severity)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_severity.background.setColorFilter(resources.getColor(R.color.text_primary), PorterDuff.Mode.SRC_ATOP)
        sp_severity.adapter = adapterSeverity
    }

    private fun initPriorityAdapter() {
        val priority = arrayOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
        val adapterSeverity = ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, priority)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_priority.background.setColorFilter(resources.getColor(R.color.text_primary), PorterDuff.Mode.SRC_ATOP)
        sp_priority.adapter = adapterSeverity
    }
}