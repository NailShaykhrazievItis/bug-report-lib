package com.itis.android.githubapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(var login: String,
                var id: String,
                var name: String,
                @SerializedName("avatar_url")
                var avatarUrl: String,
                @SerializedName("html_url")
                var htmlUrl: String,
                var company: String,
                var blog: String,
                var location: String,
                var email: String,
                var bio: String,
                @SerializedName("public_repos")
                var publicRepos: Int = 0,
                @SerializedName("public_gists")
                var publicGists: Int = 0,
                var followers: Int = 0,
                var following: Int = 0,
                @SerializedName("created_at")
                var createdAt: Date,
                @SerializedName("updated_at")
                var updatedAt: Date
)
