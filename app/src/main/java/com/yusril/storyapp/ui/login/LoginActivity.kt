package com.yusril.storyapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.yusril.storyapp.R
import com.yusril.storyapp.data.local.LoginPrefs
import com.yusril.storyapp.databinding.ActivityLoginBinding
import com.yusril.storyapp.ui.register.RegisterActivity
import com.yusril.storyapp.ui.story.StoryActivity
import com.yusril.storyapp.utils.Resource

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val viewModel :LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            Log.d("inputan","$email,$password")

            if (email.isNotEmpty() && password.isNotEmpty()){

                viewModel.loginResponse(email,password)
                viewModel.getResponseLogin().observe(this) {
                    when(it.status){
                        Resource.Status.SUCCESS ->{
                            if (it.data != null){
                                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                                val token = it.data.loginResult?.token
                                LoginPrefs(this).saveDataLogin(true,token!!)
                                Log.d("tokennya",token)
                                startActivity(Intent(this,StoryActivity::class.java))
                                finish()
                            }
                        }

                        Resource.Status.ERROR ->{
                            Toast.makeText(this, "Api ERROR ${it.message}", Toast.LENGTH_SHORT).show()
                        }

                        Resource.Status.LOADING->{
                            binding.pbLogin.visibility = View.VISIBLE
                        }

                    }
                }
            }else{
                Toast.makeText(this, "Email atau password kosong", Toast.LENGTH_SHORT).show()
            }


        }


        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}