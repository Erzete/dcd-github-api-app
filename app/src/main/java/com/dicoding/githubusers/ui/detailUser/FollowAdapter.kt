package com.dicoding.githubusers.ui.detailUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubusers.data.remote.response.FollowerResponseItem
import com.dicoding.githubusers.databinding.ItemUserBinding

class FollowAdapter : ListAdapter<FollowerResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aUser = getItem(position)
        holder.bind(aUser)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aUser: FollowerResponseItem){
            Glide.with(binding.root.context)
                .load(aUser.avatarUrl) // URL Gambar
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.imgItemPhoto) // imageView mana yang akan diterapkan
            binding.tvItemName.text = aUser.login
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowerResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowerResponseItem, newItem: FollowerResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FollowerResponseItem, newItem: FollowerResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}