package com.yusril.storyapp.data.local

import android.content.Context

class LoginPrefs(context :Context) {
    companion object{
        const val PREFS_NAME = "login_preference"
        const val PREFS_IS_LOGIN = "user_login"
        const val TOKEN = "token"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveDataLogin(typeUser : Boolean,token : String){
        val editor = prefs.edit()
        editor.putBoolean(PREFS_IS_LOGIN, typeUser)
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getDataLogin(): Boolean {
        return prefs.getBoolean(PREFS_IS_LOGIN, false)
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN,"")
    }

    fun removeDataLogin() {
        prefs.edit().remove(PREFS_IS_LOGIN).apply()
        prefs.edit().remove(TOKEN).apply()
    }

}