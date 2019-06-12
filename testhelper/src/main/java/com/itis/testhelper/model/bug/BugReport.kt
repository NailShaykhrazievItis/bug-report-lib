package com.itis.testhelper.model.bug

data class BugReport(
        var name: String,
        var summary: String,
        var reporter: String,
        var submitDate: String,
        var precondition: String,
        var environment: Environment,
        var severity: Severity,
        var priority: Priority,
        var frequency: Frequency,
        var steps: List<Step>,
        var result: String,
        var expectedResult: String,
        var comments: String = ""
) {
    fun toMarkdown(): String = StringBuilder().apply {
        append("|Label|Value|\n")
        append("|-------|-------|\n")
        appendRow("Name", name)
        appendRow("Summary", summary)
        appendRowIfNotEmpty("Precondition", precondition)
        appendRow("Version", String.format("%s.%s-%s", environment.versionName, environment.versionCode, environment.processor))
        appendRow("Device", environment.device)
        appendRow("API Level", environment.os)
        appendRow("Severity", severity.name)
        appendRow("Priority", priority.name)
        appendRow("Frequency", frequency.name)
        appendRowIfNotEmpty("Reporter", reporter)
        appendRow("Submit Date", submitDate)
        appendHeader("Steps to reproduce", initSteps())
        appendHeader("Actual results", result)
        appendHeader("Expected result", expectedResult)
    }.toString()

    private fun initSteps() = StringBuilder("").apply {
        steps.forEach {
            append("1. ${it.name}\n")
        }
    }.toString()

    private fun StringBuilder.appendRowIfNotEmpty(title: String, text: String): StringBuilder =
            if (text.isNotEmpty()) {
                this.append("|$title|${text.capitalize()}|\n")
            } else this

    private fun StringBuilder.appendRow(title: String, text: String): StringBuilder =
            this.append("|$title|${text.capitalize()}|\n")

    private fun StringBuilder.appendHeader(title: String, text: String): StringBuilder =
            this.append("### $title\n${text.capitalize()}\n")

    companion object {
        private const val TITLE_NAME = "Name"
        private const val TITLE_SUMMARY = "Summary"
        private const val TITLE_PRECONDITION = "Precondition"
    }
}