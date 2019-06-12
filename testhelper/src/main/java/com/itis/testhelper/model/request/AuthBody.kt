package com.itis.testhelper.model.request

import com.google.gson.annotations.SerializedName
import com.itis.testhelper.utils.DEFAULT_PAT

data class AuthBody(
        @SerializedName("note")
        var note: String = DEFAULT_PAT,
        @SerializedName("scopes")
        var scopes: List<String> = arrayListOf("repo", "user")
)
