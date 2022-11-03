package com.yusril.storyapp.ui.detailstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.storyapp.R
import com.yusril.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding :ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME_EXTRA")
        val description = intent.getStringExtra("DETAIL_EXTRA")
        val image = intent.getStringExtra("IMAGE_EXTRA")

        binding.apply {
            tvTitle.text = name
            tvDescription.text = description
            Glide.with(this@DetailStoryActivity)
                .load(image)
                .apply(RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background))
                .into(ivStory)
        }

    }
}