package com.itis.testhelper.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Repository(
        var id: Int = 0,
        var name: String,
        @SerializedName("full_name")
        var fullName: String,
        @SerializedName("private")
        var repPrivate: Boolean = false,
        var description: String?,
        var language: String?,
        @SerializedName("updated_at")
        var updatedAt: Date
)
