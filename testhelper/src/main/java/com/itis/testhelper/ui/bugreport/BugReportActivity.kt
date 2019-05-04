package com.itis.testhelper.ui.bugreport

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itis.testhelper.R
import com.itis.testhelper.model.Priority
import com.itis.testhelper.model.Severity
import com.itis.testhelper.repository.PreferenceRepository
import com.itis.testhelper.repository.RepositoryProvider
import kotlinx.android.synthetic.main.activity_bug_report.*
import kotlinx.coroutines.*

class BugReportActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var preferenceRepository: PreferenceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceRepository = RepositoryProvider.getPreferenceRepository(applicationContext)
        setContentView(R.layout.activity_bug_report)
        initListeners()
        initPriorityAdapter()
        initSeverityAdapter()
        val tt = preferenceRepository.getSteps()
        val b = 5
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_change, R.anim.exit_to_bottom)
    }

    private fun initListeners() {
        iv_report_back.setOnClickListener { onBackPressed() }
        btn_report_send.setOnClickListener {
            launch(Dispatchers.IO) {
                preferenceRepository.clearSteps()
            }
        }
    }

    private fun initSeverityAdapter() {
        val severity = arrayOf(Severity.BLOCKER, Severity.CRITICAL, Severity.MAJOR, Severity.MINOR, Severity.TRIVIAL)
        val adapterSeverity = ArrayAdapter<Severity>(this, android.R.layout.simple_spinner_item, severity)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_severity.background.setColorFilter(ContextCompat.getColor(this, R.color.text_primary), PorterDuff.Mode.SRC_ATOP)
        sp_severity.adapter = adapterSeverity
    }

    private fun initPriorityAdapter() {
        val priority = arrayOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
        val adapterSeverity = ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, priority)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_priority.background.setColorFilter(ContextCompat.getColor(this, R.color.text_primary), PorterDuff.Mode.SRC_ATOP)
        sp_priority.adapter = adapterSeverity
    }
}