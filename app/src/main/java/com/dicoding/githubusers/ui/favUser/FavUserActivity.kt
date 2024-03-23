package com.dicoding.githubusers.ui.favUser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.data.local.entity.FavoriteUser
import com.dicoding.githubusers.databinding.ActivityFavUserBinding
import com.dicoding.githubusers.ui.ViewModelFactory

class FavUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        val itemAnimator = DefaultItemAnimator()
        binding.rvUser.itemAnimator = itemAnimator

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favUserViewModel: FavUserViewModel by viewModels {
            factory
        }

        favUserViewModel.listUser.observe(this) { newListUsers ->
            setFavsData(newListUsers)
        }

        favUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setFavsData(newListUsers: List<FavoriteUser>) {
        val adapter = FavUserAdapter()
        adapter.submitList(newListUsers)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}