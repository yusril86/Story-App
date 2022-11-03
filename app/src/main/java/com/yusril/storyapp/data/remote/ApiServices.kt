package com.yusril.storyapp.data.remote

import com.yusril.storyapp.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponseData<Login>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String
    ): BaseResponseList<Story>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponseMessage>

    @JvmSuppressWildcards
    @POST("stories")
    @Multipart
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file : MultipartBody.Part,
        @PartMap requestBody: Map<String,RequestBody>
    ): BaseResponseMessage
}