package com.yusril.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yusril.storyapp.data.Result
import com.yusril.storyapp.data.model.BaseResponseData
import com.yusril.storyapp.data.model.BaseResponseMessage
import com.yusril.storyapp.data.model.Login
import com.yusril.storyapp.data.repository.Repository
import com.yusril.storyapp.ui.story.StoryViewModel
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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel

    @Mock private lateinit var repository: Repository

    private val dummyLogin = DataDummy.generateDummyLoginResponse()
    private val emailDummy = DataDummy.email
    private val passwordDummy = DataDummy.password

    private val scope = CoroutineScope(Dispatchers.IO)

    @Before
    fun setUp(){
        loginViewModel = LoginViewModel(repository)
    }

    @Test
    fun `when Login Should Not Null and Return Success`() {
        scope.launch {
            val expectedLogin = MutableLiveData<Result<BaseResponseData<Login>>>()
            expectedLogin.value = Result.Success(dummyLogin)
            Mockito.`when`(repository.loginResponse(emailDummy,passwordDummy)).thenReturn(expectedLogin)
            val actualLogin = loginViewModel.getResponseLogin(emailDummy,passwordDummy).getOrAwaitValue()
            Mockito.verify(repository).loginResponse(emailDummy,passwordDummy)
            Assert.assertNotNull(actualLogin)
            Assert.assertTrue(actualLogin is Result.Success)
            Assert.assertEquals(dummyLogin.message,(actualLogin as Result.Success).data.message)
        }
    }

    @Test
    fun `when Network Error Should Return Error`() {
        scope.launch {
            val expectedLogin = MutableLiveData<Result<BaseResponseData<Login>>>()
            expectedLogin.value = Result.Error("Error")
            Mockito.`when`(repository.loginResponse(emailDummy,passwordDummy)).thenReturn(expectedLogin)
            val actualLogin = loginViewModel.getResponseLogin(emailDummy,passwordDummy).getOrAwaitValue()
            Mockito.verify(repository).loginResponse(emailDummy,passwordDummy)
            Assert.assertNotNull(actualLogin)
            Assert.assertEquals(dummyLogin,(actualLogin as Result.Error))
        }
    }

}