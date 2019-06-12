package com.itis.testhelper.ui.bugreport

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.itis.testhelper.R
import com.itis.testhelper.model.bug.*
import com.itis.testhelper.repository.RepositoryProvider
import com.itis.testhelper.ui.settings.SettingsActivity
import com.itis.testhelper.utils.extensions.afterTextChanged
import com.itis.testhelper.utils.extensions.hideKeyboard
import com.itis.testhelper.utils.extensions.isValidateEmpty
import kotlinx.android.synthetic.main.activity_bug_report.*
import kotlinx.android.synthetic.main.dialog_edit_step.view.*

class BugReportActivity : AppCompatActivity(), BugReportView {

    private lateinit var presenter: BugReportPresenter

    private lateinit var adapterSeverity: ArrayAdapter<Severity>
    private lateinit var adapterPriority: ArrayAdapter<Priority>
    private lateinit var adapterFrequency: ArrayAdapter<Frequency>
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
        initFrequencyAdapter()
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
        Snackbar.make(container_bug_report, throwable.message.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun initSteps(steps: List<Step>) {
        stepsAdapter = StepsAdapter(ArrayList(steps), removeStepLambda = {
            presenter.stepRemoved(it)
        }, itemClickLambda = { position, it ->
            presenter.onItemClick(position, it)
        })
        rv_steps.adapter = stepsAdapter
    }

    override fun itemRemoved(position: Int) {
        stepsAdapter?.removeItem(position)
    }

    override fun itemAdded(step: Step) {
        stepsAdapter?.addItem(step)
    }

    override fun itemChanged(position: Int, step: Step) {
        stepsAdapter?.changeItem(position, step)
    }

    override fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun showSuccessCreateMessage(title: String) {
        Snackbar.make(container_bug_report, getString(R.string.issue_created, title), Snackbar.LENGTH_LONG).show()
    }

    override fun showChangeStepDialog(step: String) {
        AlertDialog.Builder(this).apply {
            val dialogView = LayoutInflater.from(this@BugReportActivity).inflate(R.layout.dialog_edit_step, null)
            dialogView.et_step.setText(step)
            setTitle(getString(R.string.change_step))
            setView(dialogView)
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                presenter.changeStep(dialogView.et_step.text.trimStart().trimEnd().toString())
                dialogView.et_step.hideKeyboard()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                dialogView.et_step.hideKeyboard()
            }
            show()
        }
    }

    override fun showAddStepDialog() {
        AlertDialog.Builder(this).apply {
            val dialogView = LayoutInflater.from(this@BugReportActivity).inflate(R.layout.dialog_edit_step, null)
            setTitle(getString(R.string.add_step))
            setView(dialogView)
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                presenter.addStep(dialogView.et_step.text.trimStart().trimEnd().toString())
                dialogView.et_step.hideKeyboard()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                dialogView.et_step.hideKeyboard()
            }
            show()
        }
    }

    private fun initEnvironment(): Environment {
        var versionName = "1.0.0"
        var versionCode = 1
        var processor = ""
        packageManager?.getPackageInfo(packageName, 0)?.also {
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.longVersionCode.toInt()
            } else {
                it.versionCode
            }
            versionName = it.versionName
            processor = Build.CPU_ABI
        }
        return Environment(versionName, versionCode.toString(), processor,
                "${Build.MANUFACTURER} ${Build.MODEL}", Build.VERSION.SDK_INT.toString())
    }

    private fun initListeners() {
        iv_report_back.setOnClickListener { onBackPressed() }
        iv_settings.setOnClickListener { presenter.onSettingClick() }
        tv_clear_all.setOnClickListener { presenter.onClearAllClick() }
        tv_report_add_step.setOnClickListener { presenter.onAddStepClick() }
        btn_report_send.setOnClickListener {
            if (et_report_name.isValidateEmpty { setFieldEmptyError(ti_report_name) } &&
                    et_report_summary.isValidateEmpty { setFieldEmptyError(ti_report_summary) }) {
                presenter.onSendClick(
                        title = et_report_name.text.toString(),
                        summary = et_report_summary.text.toString(),
                        precondition = et_report_precondition.text.toString(),
                        severity = sp_severity.selectedItem as Severity,
                        priority = sp_priority.selectedItem as Priority,
                        frequency = sp_frequency.selectedItem as Frequency,
                        environment = initEnvironment(),
                        actual = et_report_result_actual.text.toString(),
                        expected = et_report_result_expected.text.toString()
                )
            }
        }
        initErrorClearListener(et_report_name, ti_report_name)
        initErrorClearListener(et_report_summary, ti_report_summary)
    }

    private fun initErrorClearListener(editText: EditText?, textInputLayout: TextInputLayout?) {
        editText?.afterTextChanged {
            textInputLayout?.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun setFieldEmptyError(view: TextInputLayout) {
        view.error = getString(R.string.field_can_not_empty)
    }

    private fun initSeverityAdapter() {
        val severity = Severity.values()
        adapterSeverity = ArrayAdapter(this, android.R.layout.simple_spinner_item, severity)
        adapterSeverity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_severity.background.setColorFilter(ContextCompat.getColor(this, R.color.icon), PorterDuff.Mode.SRC_ATOP)
        sp_severity.adapter = adapterSeverity
    }

    private fun initPriorityAdapter() {
        val priority = Priority.values()
        adapterPriority = ArrayAdapter(this, android.R.layout.simple_spinner_item, priority)
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_priority.background.setColorFilter(ContextCompat.getColor(this, R.color.icon), PorterDuff.Mode.SRC_ATOP)
        sp_priority.adapter = adapterPriority
    }

    private fun initFrequencyAdapter() {
        val frequency = Frequency.values()
        adapterFrequency = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequency)
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_frequency.background.setColorFilter(ContextCompat.getColor(this, R.color.icon), PorterDuff.Mode.SRC_ATOP)
        sp_frequency.adapter = adapterFrequency
    }
}