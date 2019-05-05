package com.itis.testhelper.ui.bugreport

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itis.testhelper.R
import com.itis.testhelper.model.Priority
import com.itis.testhelper.model.Severity
import com.itis.testhelper.model.Step
import com.itis.testhelper.repository.RepositoryProvider
import kotlinx.android.synthetic.main.activity_bug_report.*

class BugReportActivity : AppCompatActivity(), BugReportView {

    private lateinit var presenter: BugReportPresenter

    private lateinit var adapterSeverity: ArrayAdapter<Severity>
    private lateinit var adapterPriority: ArrayAdapter<Priority>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bug_report)
        presenter = BugReportPresenter(this,
                RepositoryProvider.getPreferenceRepository(applicationContext),
                RepositoryProvider.issueRepository
        )
        initListeners()
        initPriorityAdapter()
        initSeverityAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_change, R.anim.exit_to_bottom)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(throwable: Throwable) {
    }

    override fun initSteps(steps: List<Step>) {
    }

    override fun navigateToAuth() {
    }

    override fun showSuccessCreateMessage(title: String) {

    }

    private fun initListeners() {
        iv_report_back.setOnClickListener { onBackPressed() }
        btn_report_send.setOnClickListener { presenter.onSendClick(et_report_name.text.toString()) }
    }

    private fun initSeverityAdapter() {
        val severity = arrayOf(Severity.BLOCKER, Severity.CRITICAL, Severity.MAJOR, Severity.MINOR, Severity.TRIVIAL)
        adapterSeverity = ArrayAdapter(this, android.R.layout.simple_spinner_item, severity)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_severity.background.setColorFilter(ContextCompat.getColor(this, R.color.text_primary), PorterDuff.Mode.SRC_ATOP)
        sp_severity.adapter = adapterSeverity
    }

    private fun initPriorityAdapter() {
        val priority = arrayOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
        adapterPriority = ArrayAdapter(this, android.R.layout.simple_spinner_item, priority)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_priority.background.setColorFilter(ContextCompat.getColor(this, R.color.text_primary), PorterDuff.Mode.SRC_ATOP)
        sp_priority.adapter = adapterSeverity
    }
}