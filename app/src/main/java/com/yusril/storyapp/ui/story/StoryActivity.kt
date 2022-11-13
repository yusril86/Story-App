package com.yusril.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yusril.storyapp.data.local.LoginPrefs
import com.yusril.storyapp.databinding.ActivityStoryBinding
import com.yusril.storyapp.ui.maps.MapsActivity
import com.yusril.storyapp.ui.newstory.NewStoryActivity
import com.yusril.storyapp.ui.splash.SplashActivity

class StoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStoryBinding
    private val viewModel : StoryViewModel by viewModels(){
        ViewModelFactoryStory(this)
    }
    private val storyAdapter : StoryAdapter = StoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getStories()


        binding.btnNewStory.setOnClickListener {
            startActivity(Intent(this,NewStoryActivity::class.java))
        }

        binding.btnMaps.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
        }

        binding.toolbar.apply {
            btnLogout.setOnClickListener { logOut() }
        }
    }

    private fun getStories(){
        /*val tokenPref = LoginPrefs(this).getToken()
        Log.d("getToken", tokenPref.toString())
        viewModel.fetchResponseStory("Bearer $tokenPref")
        viewModel.getStories().observe(this){ it ->
            when(it.status){
                Resource.Status.SUCCESS ->{
                    if (it.data?.listStory != null){
                        binding.pbStory.visibility = View.INVISIBLE
                       *//* binding.rvStory.apply {
                            storyAdapter.withLoadStateFooter(
                                footer = LoadingStateAdapter {
                                    storyAdapter.retry()
                                }
                            )
                            adapter = storyAdapter
                        }*//*
//                        storyAdapter.updateAdapter(it.data.listStory)
                        val adapter = StoryAdapter()
                        binding.rvStory.adapter = adapter.withLoadStateFooter(
                            footer = LoadingStateAdapter {
                                adapter.retry()
                            }
                        )
                        viewModel.getStory("Bearer $tokenPref").observe(this){
                            adapter.submitData(lifecycle,it)
                        }
                    }

                }

                Resource.Status.LOADING ->{
                    binding.pbStory.visibility = View.VISIBLE
                }

                Resource.Status.ERROR->{
                    binding.pbStory.visibility = View.INVISIBLE
                }
            }
        }*/

        val tokenPref = LoginPrefs(this).getToken()
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getStory("Bearer $tokenPref").observe(this){
            adapter.submitData(lifecycle,it)
        }
    }

    private fun logOut(){
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertBuilder.setCancelable(false)
        alertBuilder.setMessage("Yakin Logout?")
        alertBuilder.setPositiveButton("Ya"){_,_  ->
            val intent =Intent(this,SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
            LoginPrefs(this).removeDataLogin()

        }
        alertBuilder.setNegativeButton("Tidak"){dialog,_ ->
            dialog.cancel()
        }
        val alert : AlertDialog = alertBuilder.create()
        alert.show()

    }

}