package com.yusril.storyapp.di

import android.content.Context
import com.yusril.storyapp.data.local.database.StoryDatabase
import com.yusril.storyapp.data.remote.ApiClient
import com.yusril.storyapp.data.repository.Repository

object Injection {
    fun provideRepository(context: Context):Repository{
        val apiService = ApiClient.API_SERVICE
        val database = StoryDatabase.getDatabase(context)
        return Repository(apiService,database)
    }



}