package com.yusril.storyapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusril.storyapp.data.model.BaseResponseData
import com.yusril.storyapp.data.model.Login
import com.yusril.storyapp.data.remote.ApiClient
import com.yusril.storyapp.data.remote.ApiServices
import com.yusril.storyapp.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val mResponseLogin = MutableLiveData<Resource<BaseResponseData<Login>>>()

    fun loginResponse(email:String, password:String){
        viewModelScope.launch {
            mResponseLogin.postValue(Resource.loading(null))
            val response = ApiClient.API_SERVICE.login(email,password)
            try {
                mResponseLogin.postValue(Resource.success(data = response))
            }catch (e:Exception){
                mResponseLogin.postValue(Resource.error(data = null,e.toString()))
            }
        }
    }

    fun getResponseLogin(): MutableLiveData<Resource<BaseResponseData<Login>>> {
        return mResponseLogin
    }

}