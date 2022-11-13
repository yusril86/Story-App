package com.yusril.storyapp.utils

data class Resource<out T>(val data : T?, val message:String?, val status: Status){
    enum class Status{
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object{
        fun <T> success(data: T?):Resource<T>{
            return Resource(data,null,Status.SUCCESS)
        }

        fun <T> loading (data:T? = null):Resource<T>{
            return Resource(data, null, Status.LOADING)
        }

        fun <T> error(data:T? = null,message: String?):Resource<T>{
            return Resource(data,message,Status.ERROR)
        }
    }

}