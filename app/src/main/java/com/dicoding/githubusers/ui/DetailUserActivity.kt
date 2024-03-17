package com.dicoding.githubusers.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubusers.R
import com.dicoding.githubusers.data.response.UserDetailResponse
import com.dicoding.githubusers.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        private const val KEY_USER = "key_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(KEY_USER)

        if (savedInstanceState == null) {
            if (username != null) {
                detailViewModel.getDetailUser(username)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.detailUser.observe(this) { aUser ->
            setDetailUser(aUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailUser(aUser: UserDetailResponse) {
        binding.name.text = aUser.name ?: aUser.login
        binding.username.text = aUser.login
        Glide.with(this)
            .load(aUser.avatarUrl) // URL Gambar
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.detailPhoto) // imageView mana yang akan diterapkan
        val followerCount = this@DetailUserActivity.resources.getString(R.string.follower, aUser.followers)
        binding.follower.text = followerCount
        val followingCount = this@DetailUserActivity.resources.getString(R.string.follower, aUser.following)
        binding.following.text = followingCount
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}