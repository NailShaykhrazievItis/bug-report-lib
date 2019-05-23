package com.itis.testhelper.model.response

import com.google.gson.annotations.SerializedName

data class Authorization(@SerializedName("token")
                         var token: String)
