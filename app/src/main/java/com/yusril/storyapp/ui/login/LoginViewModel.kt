package com.yusril.storyapp.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.data.repository.Repository
import com.yusril.storyapp.di.Injection

class LoginViewModel(private val repository: Repository) : ViewModel() {

    fun getResponseLogin(
        email: String,
        password: String
    ) = repository.loginResponse(email, password)



}
class ViewModelFactoryLogin (private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}