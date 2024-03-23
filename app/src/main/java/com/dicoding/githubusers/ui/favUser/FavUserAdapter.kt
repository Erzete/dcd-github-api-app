package com.dicoding.githubusers.ui.favUser

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubusers.data.local.entity.FavoriteUser
import com.dicoding.githubusers.databinding.ItemUserBinding
import com.dicoding.githubusers.ui.detailUser.DetailUserActivity

class FavUserAdapter : ListAdapter<FavoriteUser, FavUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aUser = getItem(position)
        holder.bind(aUser)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aUser: FavoriteUser){
            Glide.with(binding.root.context)
                .load(aUser.avatarUrl) // URL Gambar
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.imgItemPhoto) // imageView mana yang akan diterapkan
            binding.tvItemName.text = aUser.username

            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailUserActivity::class.java)
                intentDetail.putExtra("key_user", aUser.username)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }

}