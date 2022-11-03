package com.yusril.storyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusril.storyapp.data.model.BaseResponseMessage
import com.yusril.storyapp.data.remote.ApiClient
import com.yusril.storyapp.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class RegisterViewModel:ViewModel() {
    private val mResponseRegister = MutableLiveData<Resource<Response<BaseResponseMessage>>>()
    private val mErrorMessage = MutableLiveData<String>()

    fun fetchRegister(name:String, email:String, password:String){
        viewModelScope.launch {
            mResponseRegister.postValue(Resource.loading(null))
            val  response = ApiClient.API_SERVICE.register(name,email,password)

            try {
                if (response.isSuccessful){
                    mResponseRegister.postValue(Resource.success(response))
                }else{
                    val errorMessage =
                    try {
                        val responseError = JSONObject(response.errorBody().toString())
//                        val objResponseError = responseError.getJSONObject("error")
                        responseError.getString("message")

                    } catch (e: Exception) {
                        e.message ?: ""
                    }
//                    mErrorMessage.value = errorMessage.toString()
                    mResponseRegister.postValue(Resource.error(null,errorMessage))
                }
            }catch (e:Exception){
                mResponseRegister.postValue(Resource.error(null, e.toString()))
            }
        }
    }
    fun getResponseRegister():MutableLiveData<Resource<Response<BaseResponseMessage>>>{
        return mResponseRegister
    }

//    fun onError(): LiveData<String> {
//        return mErrorMessage
//    }
}