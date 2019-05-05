package com.itis.testhelper.model.request

import com.itis.testhelper.utils.STRING_EMPTY

data class IssueBody(
        var title: String,
        var body: String = STRING_EMPTY,
        var labels: List<String> = arrayListOf("bug")
)
