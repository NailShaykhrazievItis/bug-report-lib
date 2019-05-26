package com.itis.testhelper.ui.bugreport

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itis.testhelper.R
import com.itis.testhelper.model.Priority
import com.itis.testhelper.model.Severity
import com.itis.testhelper.model.Step
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_bug_report.*
import kotlinx.android.synthetic.main.dialog_edit_step.view.*

class BugReportActivity : AppCompatActivity(), BugReportView {

    private lateinit var presenter: BugReportPresenter

    private lateinit var adapterSeverity: ArrayAdapter<Severity>
    private lateinit var adapterPriority: ArrayAdapter<Priority>
    private var stepsAdapter: StepsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bug_report)
        presenter = BugReportPresenter(this,
                RepositoryProvider.getStepsRepository(applicationContext),
                RepositoryProvider.getUserRepository(applicationContext),
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
        pb_steps.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_steps.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun initSteps(steps: List<Step>) {
        stepsAdapter = StepsAdapter(ArrayList(steps), removeStepLambda = {
            presenter.stepRemoved(it)
        }, itemClickLambda = {
            presenter.onItemClick(it)
        })
        rv_steps.adapter = stepsAdapter
    }

    override fun itemRemoved(position: Int) {
        stepsAdapter?.removeItem(position)
    }

    override fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun showSuccessCreateMessage(title: String) {

    }

    override fun showChangeStepDialog(step: String) {
        AlertDialog.Builder(this).apply {
            val dialogView = LayoutInflater.from(this@BugReportActivity).inflate(R.layout.dialog_edit_step, null)
            dialogView.et_step.setText(step)
            setTitle(getString(R.string.change_step))
            setView(dialogView)
            setPositiveButton("OK") { _, _ ->
                //                presenter.saveRepoName(dialogView.et_repo.text.toString())
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun initListeners() {
        iv_report_back.setOnClickListener { onBackPressed() }
        iv_settings.setOnClickListener { presenter.onSettingClick() }
        tv_clear_all.setOnClickListener { presenter.onClearAllClick() }
        tv_report_add_step.setOnClickListener { presenter.onAddStepClick() }
        btn_report_send.setOnClickListener { presenter.onSendClick(et_report_name.text.toString()) }
    }

    private fun initSeverityAdapter() {
        val severity = arrayOf(Severity.BLOCKER, Severity.CRITICAL, Severity.MAJOR, Severity.MINOR, Severity.TRIVIAL)
        adapterSeverity = ArrayAdapter(this, android.R.layout.simple_spinner_item, severity)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_severity.background.setColorFilter(ContextCompat.getColor(this, R.color.icon), PorterDuff.Mode.SRC_ATOP)
        sp_severity.adapter = adapterSeverity
    }

    private fun initPriorityAdapter() {
        val priority = arrayOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
        adapterPriority = ArrayAdapter(this, android.R.layout.simple_spinner_item, priority)
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_priority.background.setColorFilter(ContextCompat.getColor(this, R.color.icon), PorterDuff.Mode.SRC_ATOP)
        sp_priority.adapter = adapterPriority
    }
}