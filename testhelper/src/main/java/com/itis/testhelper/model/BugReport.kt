package com.itis.testhelper.model

data class BugReport(
        var name: String,
        var summary: String,
        var reporter: String,
        var submitDate: String,
        var precondition: String,
        var version: String,
        var severity: Severity,
        var priority: Priority,
        var frequency: Frequency,
        var steps: List<Step>,
        var result: String,
        var expectedResult: String
) {
    fun toMarkdown(): String {
        StringBuilder().apply {
            append("|Name|$name|${initTableLine("Name", name)}")
            append("|Summary|$summary|${initTableLine("Summary", summary)}")
            append("|Reporter|$reporter|${initTableLine("Reporter", reporter)}")
            append("|Submit Date|$submitDate|${initTableLine("Submit Date", submitDate)}")
            append("|Precondition|$precondition|${initTableLine("Precondition", precondition)}")
            append("|Version|$version|${initTableLine("Version", version)}")
            append("|Severity|${severity.name}|${initTableLine("Severity", severity.name)}")
            append("|Priority|${priority.name}|${initTableLine("Priority", priority.name)}")
            append("|Frequency|${frequency.name}|${initTableLine("Frequency", frequency.name)}")
        }
        return ""
    }

    private fun initTableLine(title: String, text: String): String =
            StringBuilder("\n|-").apply {
                append(getHyphenByText(title))
                append("-|-")
                append(getHyphenByText(text))
                append("-|\n")
            }.toString()

    private fun getHyphenByText(title: String): String {
        var result1 = ""
        repeat(title.length) {
            result1 += "-"
        }
        return result1
    }

    companion object {
        private const val TITLE_NAME = "Name"
        private const val TITLE_SUMMARY = "Summary"
        private const val TITLE_PRECONDITION = "Precondition"
    }
}