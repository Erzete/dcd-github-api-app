package com.dicoding.githubusers.ui.mainPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusers.data.local.UserRepository
import com.dicoding.githubusers.data.remote.response.GithubUser
import com.dicoding.githubusers.data.remote.response.UserResponse
import com.dicoding.githubusers.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val userRepository: UserRepository) : ViewModel(){
    private val _listUser = MutableLiveData<List<GithubUser>>()
    val listUser : LiveData<List<GithubUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val USERNAME = "erzete"
    }

    init {
        getUsers(USERNAME)
    }

    fun getUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.users
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}