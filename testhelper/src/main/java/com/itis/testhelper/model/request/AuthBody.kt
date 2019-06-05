package com.itis.testhelper.model.request

data class AuthBody(
        var note: String = "BugReportLib2",
        var scopes: List<String> = arrayListOf("repo")
)