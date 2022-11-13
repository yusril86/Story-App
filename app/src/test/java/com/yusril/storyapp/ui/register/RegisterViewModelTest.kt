package com.yusril.storyapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yusril.storyapp.data.model.BaseResponseMessage
import com.yusril.storyapp.data.repository.Repository
import com.yusril.storyapp.utils.DataDummy
import com.yusril.storyapp.utils.Resource
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
class RegisterViewModelTest  {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerViewModel: RegisterViewModel

    @Mock private lateinit var repository: Repository

    private val dummyRegister = DataDummy.generateDummyStoryMessage()
    private val emailDummy = DataDummy.emailRegister
    private val passwordDummy = DataDummy.password
    private val nameDummy = DataDummy.name

    private val scope = CoroutineScope(Dispatchers.IO)

    @Before
    fun setUp(){
        registerViewModel = RegisterViewModel(repository)
    }

    @Test
    fun `when Register Should Not Null`() {
        scope.launch {
            val expectedRegister = MutableLiveData<Resource<BaseResponseMessage>>()
            expectedRegister.value = Resource.success(dummyRegister)
            Mockito.`when`(repository.register(nameDummy,emailDummy,passwordDummy)).thenReturn(expectedRegister)
            val actualRegister = registerViewModel.registerUser(nameDummy,emailDummy,passwordDummy).getOrAwaitValue()
            Mockito.verify(repository).register(nameDummy,emailDummy,passwordDummy)
            Assert.assertNotNull(actualRegister)
        }
    }

    @Test
    fun `when Network Error Should Return Error`() {
        scope.launch {
            val expectedRegister = MutableLiveData<Resource<BaseResponseMessage>>()
            expectedRegister.value = Resource.error(null,"Error")
            Mockito.`when`(repository.register(nameDummy,emailDummy,passwordDummy)).thenReturn(expectedRegister)
            val actualRegister = registerViewModel.registerUser(nameDummy,emailDummy,passwordDummy).getOrAwaitValue()
            Mockito.verify(repository).register(nameDummy,emailDummy,passwordDummy)
            Assert.assertNotNull(actualRegister)
        }
    }
}