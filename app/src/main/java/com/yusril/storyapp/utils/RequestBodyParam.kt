package com.yusril.storyapp.utils

import okhttp3.MultipartBody
import okhttp3.RequestBody

class RequestBodyParam {

    fun createPartFromString(text: String) : RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, text
        )
    }
}