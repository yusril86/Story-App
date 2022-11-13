package com.yusril.storyapp.ui.story

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yusril.storyapp.data.model.Story
import com.yusril.storyapp.data.repository.Repository
import com.yusril.storyapp.di.Injection
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val  repository: Repository) : ViewModel() {


    /*private val mResponseStory = MutableLiveData<Resource<BaseResponseList<Story>>>()
    fun fetchResponseStory(token :String){
        viewModelScope.launch {
            mResponseStory.postValue(Resource.loading(null))
            try {
                val response = ApiClient.API_SERVICE.getStories(token)
                mResponseStory.postValue(Resource.success(response))
            }catch (e:Exception){
                mResponseStory.postValue(Resource.error(null,e.toString()))
            }
        }
    }
    fun getStories():MutableLiveData<Resource<BaseResponseList<Story>>>{
        return mResponseStory
    }*/


    fun getStory(token: String) : LiveData<PagingData<Story>> =
        repository.getStory(token).cachedIn(viewModelScope)



    fun addStory(token: String,
                  file: MultipartBody.Part,
                  requestBody: Map<String, RequestBody>) =
        repository.addStory(token,file,requestBody)


    fun mapStory(token: String) = repository.mapStory(token)

}
class ViewModelFactoryStory (private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
