package com.dicoding.githubusers.ui.mainPage

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubusers.data.remote.response.GithubUser
import com.dicoding.githubusers.databinding.ItemUserBinding
import com.dicoding.githubusers.ui.detailUser.DetailUserActivity

class UserAdapter : ListAdapter<GithubUser, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aUser = getItem(position)
        holder.bind(aUser)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aUser: GithubUser){
            Glide.with(binding.root.context)
                .load(aUser.avatarUrl) // URL Gambar
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.imgItemPhoto) // imageView mana yang akan diterapkan
            binding.tvItemName.text = aUser.login

            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailUserActivity::class.java)
                intentDetail.putExtra("key_user", aUser.login)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }

}