package com.yusril.storyapp.ui.story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusril.storyapp.data.model.BaseResponseList
import com.yusril.storyapp.data.model.Story
import com.yusril.storyapp.data.remote.ApiClient
import com.yusril.storyapp.utils.Resource
import kotlinx.coroutines.launch

class StoryViewModel : ViewModel() {
    private val mResponseStory = MutableLiveData<Resource<BaseResponseList<Story>>>()

    fun fetchResponseStory(token :String){
        viewModelScope.launch {
            mResponseStory.postValue(Resource.loading(null))
            val response = ApiClient.API_SERVICE.getStories(token)
            try {
                mResponseStory.postValue(Resource.success(response))
            }catch (e:Exception){
                mResponseStory.postValue(Resource.error(null,e.toString()))
            }
        }
    }

    fun getStories():MutableLiveData<Resource<BaseResponseList<Story>>>{
        return mResponseStory
    }
}