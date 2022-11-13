package com.yusril.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.yusril.storyapp.data.Result
import com.yusril.storyapp.data.model.*
import com.yusril.storyapp.data.paging.StoryPagingSource
import com.yusril.storyapp.data.remote.ApiClient
import com.yusril.storyapp.data.remote.ApiServices
import com.yusril.storyapp.utils.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(private val apiServices: ApiServices) {

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Resource<BaseResponseMessage>> =
        liveData {
            emit(Resource.loading(null))
            try {
                val response = ApiClient.API_SERVICE.register(name, email, password)
                emit(Resource.success(response))

            } catch (exception: Exception) {

                emit(Resource.error(null, exception.toString()))
            }
        }

    fun loginResponse(
        email: String,
        password: String
    ): LiveData<Result<BaseResponseData<Login>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServices.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getStory(token: String): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiServices, token)
            }
        ).liveData
    }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        requestBody: Map<String, RequestBody>,
    ): LiveData<Result<BaseResponseMessage>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServices.addStory(token, file, requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun mapStory(token: String): LiveData<Result<BaseResponseList<Story>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServices.getStoriesMap(token)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}


