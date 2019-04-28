package com.itis.testhelper.model

data class BugReport(
        var name: String,
        var precondition: String,
        var version: String,
        var severity: Severity,
        var priority: Priority,
        var steps: List<Step>,
        var result: String,
        var expectedResult: String
)