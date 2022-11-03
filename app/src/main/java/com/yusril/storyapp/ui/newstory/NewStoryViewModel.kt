package com.yusril.storyapp.ui.newstory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusril.storyapp.data.model.BaseResponseMessage
import com.yusril.storyapp.data.remote.ApiClient
import com.yusril.storyapp.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class NewStoryViewModel : ViewModel() {
    private val mResponseMessageAddStory = MutableLiveData<Resource<BaseResponseMessage>>()

    fun fetchResponseMessageAddStory(
        token: String,
        file: MultipartBody.Part,
        requestBody: Map<String, RequestBody>
    ) {
        viewModelScope.launch {
            mResponseMessageAddStory.postValue(Resource.loading(data = null))
            val response = ApiClient.API_SERVICE.addStory(token, file, requestBody)
            try {
                mResponseMessageAddStory.postValue(Resource.success(data = response))
            } catch (e: Exception) {
                Log.d("addStory", e.toString())
                mResponseMessageAddStory.postValue(Resource.error(data = null, e.toString()))
            }

        }
    }

    fun getResponseMessageAddStory(): MutableLiveData<Resource<BaseResponseMessage>>{
        return mResponseMessageAddStory
    }
}