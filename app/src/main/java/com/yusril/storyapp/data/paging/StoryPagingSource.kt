package com.yusril.storyapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yusril.storyapp.data.model.Story
import com.yusril.storyapp.data.remote.ApiServices

class StoryPagingSource(private val apiServices: ApiServices, private val token: String,): PagingSource<Int,Story>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiServices.getStories(token,position,params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey =  if (responseData.listStory.isEmpty()) null else position + 1
            )
        }catch (exception : Exception){
            return LoadResult.Error(exception)
        }
    }
}