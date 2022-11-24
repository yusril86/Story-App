package com.yusril.storyapp.ui.story

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.storyapp.R
import com.yusril.storyapp.data.model.Story
import com.yusril.storyapp.databinding.ItemListStoryBinding
import com.yusril.storyapp.ui.detailstory.DetailStoryActivity

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.ItemView>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val binding =
            ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemView(binding)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.binding(data)
        }
        /*holder.binding.apply {
            mListStory[position].apply {
                Glide.with(holder.itemView.context)
                    .load(photoUrl)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background))
                    .into(ivStory)
                tvNameStory.text = name
                tvDescStory.text = description
                tvLanStory.text = "Lan = ${lan.toString()}"
                tvLatStory.text = "Lat = ${lat.toString()}"

                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context,DetailStoryActivity::class.java)
                    intent.putExtra("NAME_EXTRA",name)
                    intent.putExtra("DETAIL_EXTRA",description)
                    intent.putExtra("IMAGE_EXTRA",photoUrl)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }*/
    }

//    override fun getItemCount(): Int {
//        return mListStory.size
//    }

    inner class ItemView(val binding: ItemListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding (data: Story) {
            binding.tvNameStory.text = data.name
            binding.tvDescStory.text = data.description
            binding.tvLanStory.text = "Lan = ${data.lon.toString()}"
            binding.tvLatStory.text = "Lat = ${data.lat.toString()}"
                Glide.with(binding.ivStory)
                    .load(data.photoUrl)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background))
                    .into(binding.ivStory)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,DetailStoryActivity::class.java)
                intent.putExtra("NAME_EXTRA",data.name)
                intent.putExtra("DETAIL_EXTRA",data.description)
                intent.putExtra("IMAGE_EXTRA",data.photoUrl)
                itemView.context.startActivity(intent)
            }
        }
    }
    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(
                oldItem: Story,
                newItem: Story
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Story,
                newItem: Story
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}


