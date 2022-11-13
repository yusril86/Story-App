package com.yusril.storyapp.data.model

import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("lat")
    val lat: Double? = 0.0,
    @SerializedName("lon")
    val lon: Double? = 0.0,

)
