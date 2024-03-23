package com.dicoding.githubusers.ui.mainPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.R
import com.dicoding.githubusers.data.remote.response.GithubUser
import com.dicoding.githubusers.databinding.ActivityMainBinding
import com.dicoding.githubusers.ui.SettingActivity
import com.dicoding.githubusers.ui.SettingViewModel
import com.dicoding.githubusers.ui.ViewModelFactory
import com.dicoding.githubusers.ui.favUser.FavUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }
        val settingViewModel: SettingViewModel by viewModels {
            factory
        }
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getUsers(searchView.text.toString())
                    false
                }
        }

        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intentDetail = Intent(this@MainActivity, FavUserActivity::class.java)
                    startActivity(intentDetail)
                    true
                }
                R.id.menu2 -> {
                    val intentDetail = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intentDetail)
                    true
                }
                else -> false
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { newListUsers ->
            setUsersData(newListUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }
    private fun setUsersData(newListUsers: List<GithubUser>) {
        val adapter = UserAdapter()
        adapter.submitList(newListUsers)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}