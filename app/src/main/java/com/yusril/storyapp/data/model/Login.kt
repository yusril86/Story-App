package com.yusril.storyapp.data.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("userId")
    val userId : String? = null,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("token")
    val token : String? = null,
)
