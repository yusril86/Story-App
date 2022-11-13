package com.yusril.storyapp.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.yusril.storyapp.data.Result
import com.yusril.storyapp.data.model.BaseResponseList
import com.yusril.storyapp.data.model.BaseResponseMessage
import com.yusril.storyapp.data.model.Story
import com.yusril.storyapp.data.repository.Repository
import com.yusril.storyapp.utils.DataDummy
import com.yusril.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var storyViewModel: StoryViewModel

    @Mock
    private lateinit var repository : Repository

    private val dummyStory = DataDummy.generateDummyStoryEntity()
    private val dummyStoryMessage = DataDummy.generateDummyStoryMessage()
    private val dummyStoryResponse = DataDummy.generateDummyStoryResponse()

    private val token = DataDummy.token
    private val file = DataDummy.fileDummy
    private val map = DataDummy.mapDummy()

    private val scope = CoroutineScope(Dispatchers.IO)

    @Before
    fun setUp(){
        storyViewModel = StoryViewModel(repository)
    }


    @Test
    fun `when Get Stories Should Not Null`() {
        scope.launch {
            val expectedStory = MutableLiveData<PagingData<Story>>()
            expectedStory.value = PagingData.from(dummyStory)
            `when`(repository.getStory(token)).thenReturn(expectedStory)
            val actualStory = storyViewModel.getStory(token).getOrAwaitValue()
            Mockito.verify(repository).getStory(token)
            Assert.assertNotNull(actualStory)
        }
    }

    @Test
    fun `when Add Story Should Not Null and Return Success`() {
        scope.launch {
            val expectedStory = MutableLiveData<Result<BaseResponseMessage>>()
            expectedStory.value = Result.Success(dummyStoryMessage)
            `when`(repository.addStory(token,file,map)).thenReturn(expectedStory)
            val actualStory = storyViewModel.addStory(token,file,map).getOrAwaitValue()
            Mockito.verify(repository).addStory(token,file,map)
            Assert.assertNotNull(actualStory)
            Assert.assertTrue(actualStory is Result.Success)
            Assert.assertEquals(dummyStoryMessage.message,(actualStory as Result.Success).data.message)
        }
    }

    @Test
    fun `when Story Maps Should Not Null and Return Success`() {
        scope.launch {
            val expectedStory = MutableLiveData<Result<BaseResponseList<Story>>>()
            expectedStory.value = Result.Success(dummyStoryResponse)
            `when`(repository.mapStory(token)).thenReturn(expectedStory)
            val actualStory = storyViewModel.mapStory(token).getOrAwaitValue()
            Mockito.verify(repository).mapStory(token)
            Assert.assertNotNull(actualStory)
            Assert.assertTrue(actualStory is Result.Success)
            Assert.assertEquals(dummyStoryMessage.message,(actualStory as Result.Success).data.message)
        }
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedStory = MutableLiveData<Result<BaseResponseList<Story>>>()
        expectedStory.value = Result.Error("Error")
        `when`(repository.mapStory(token)).thenReturn(expectedStory)
        val actualStory = storyViewModel.mapStory(token).getOrAwaitValue()
        Mockito.verify(repository).mapStory(token)
        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is Result.Error)
    }

}