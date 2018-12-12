package com.itis.android.githubapp.model.search

import com.google.gson.annotations.SerializedName
import com.itis.android.githubapp.model.User

data class SearchUserResponse(
        @SerializedName("total_count")
        var count: Int,
        @SerializedName("incomplete_results")
        var isIncomplete: Boolean,
        var items: List<User>
)
