package com.yusril.storyapp.utils

import com.yusril.storyapp.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

object DataDummy {


    const val token =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTFfa1BzNVp5RWlwblZTNGsiLCJpYXQiOjE2NjYxODU5NDF9.qFQTSEKsrDXvHT3_BRdJhCrmocJoQU9ZwyM5B8AH4BM"
    const val email = "yusril@dicoding.com"
    const val password = "123456"
    val login = Login("user-1_kPs5ZyEipnVS4k","yusril", token)

     val emailRegister = "yusril123@gmail.com"
     const val name = "yusril"


     val fileDummy : MultipartBody.Part = MultipartBody.Part.Companion.createFormData("images","DataImages")
    fun mapDummy ():HashMap<String, RequestBody>{
        val map = hashMapOf<String,RequestBody>()
        map["description"] = Common.createPartFromString("desc")

        return map
    }


    fun generateDummyStoryEntity():List<Story>{
        val storyList : ArrayList<Story> = arrayListOf()
        for (i in 1..100){
            val stories = Story(
                "$i",
                "yusril",
                "ini testing $i",
                "https://story-api.dicoding.dev/images/stories/photos-1667890685376_dNDMU69I.jpg",
                "2022-11-21T02:20:30.042Z",
                -7.0595841,
                -7.0595841,
            )
            storyList.add(stories)
        }
        return storyList
    }


    fun generateDummyStoryResponse() =
        BaseResponseList(generateDummyStoryEntity(), "Stories fetched successfully", false)

    fun generateDummyLoginResponse() =
        BaseResponseData("Login successfully", false, login)

    fun generateDummyStoryMessage() =
        BaseResponseMessage( "Stories fetched successfully", false)
}