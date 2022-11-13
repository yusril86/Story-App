package com.yusril.storyapp.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.data.repository.Repository
import com.yusril.storyapp.di.Injection

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    fun registerUser(name: String, email: String, password: String) =
        repository.register(name,email,password)

}

class ViewModelFactoryRegister(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}