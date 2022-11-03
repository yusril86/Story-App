package com.yusril.storyapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yusril.storyapp.R
import com.yusril.storyapp.data.local.LoginPrefs
import com.yusril.storyapp.ui.login.LoginActivity
import com.yusril.storyapp.ui.story.StoryActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_sceen)
        Handler().postDelayed({
            val isLogin = LoginPrefs(this).getDataLogin()
            if (isLogin) {
                startActivity(Intent(this, StoryActivity::class.java))
                finish()
            } else {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000L)
    }
}