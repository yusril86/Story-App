package com.yusril.storyapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yusril.storyapp.databinding.ActivityRegisterBinding
import com.yusril.storyapp.ui.login.LoginActivity
import com.yusril.storyapp.utils.Resource

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener {
//            errorMessage()
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            viewModel.fetchRegister(name, email, password)
            viewModel.getResponseRegister().observe(this) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {

                        binding.pbRegister.visibility = View.GONE
                        Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,LoginActivity::class.java))
//                        Toast.makeText(this, "${it.data.message()}", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    Resource.Status.LOADING -> {
                        binding.pbRegister.visibility = View.VISIBLE
                    }

                    Resource.Status.ERROR -> {
                        binding.pbRegister.visibility = View.GONE

                        Toast.makeText(this, "Tidak dapat register", Toast.LENGTH_SHORT).show()
                        Log.d("errorMessageE", it.data.toString())
                    }
                }
            }

        }



    }

    /*private fun errorMessage(){
        viewModel.onError().observe(this) {
            binding.pbRegister.visibility = View.GONE
            Log.d("errorMessageE", it)

            Toast.makeText(this, "Tidak dapat register", Toast.LENGTH_SHORT).show()
        }
    }*/
}