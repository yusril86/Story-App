package com.yusril.storyapp.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponseList<T>(
    @field:SerializedName("listStory")
    val listStory: List<T> ,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("error")
    val error: Boolean
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
    val error: Boolean? = null,
    @SerializedName("loginResult")
    val loginResult: T
)


