package com.itis.android.githubapp.model.search

import com.google.gson.annotations.SerializedName
import com.itis.android.githubapp.model.Repository

data class SearchRepoResponse(
        @SerializedName("total_count")
        var count: Int,
        @SerializedName("incomplete_results")
        var isIncomplete: Boolean,
        var items: List<Repository>
)
