package com.itis.android.githubapp.model

import com.google.gson.annotations.SerializedName

data class Authorization(@SerializedName("token")
                         var token: String)