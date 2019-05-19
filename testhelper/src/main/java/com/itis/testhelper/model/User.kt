package com.itis.testhelper.model

import com.google.gson.annotations.SerializedName

data class User(var login: String,
                var id: String,
                var name: String,
                @SerializedName("avatar_url")
                var avatarUrl: String,
                @SerializedName("html_url")
                var htmlUrl: String,
                var email: String
)