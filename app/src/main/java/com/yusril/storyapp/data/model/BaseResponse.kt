package com.yusril.storyapp.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponseList<T>(
    @SerializedName("listStory")
    val listStory: List<T>? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("error")
    val error: String? = null
)

data class BaseResponseMessage(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("error")
    val error: Boolean? = null
)

data class BaseResponseData<T>(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("loginResult")
    val loginResult: T? = null
)


