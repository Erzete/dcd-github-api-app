package com.dicoding.githubusers.ui.favUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusers.data.local.UserRepository
import com.dicoding.githubusers.data.local.entity.FavoriteUser
import com.dicoding.githubusers.data.remote.response.UserDetailResponse

class FavUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _listUser = MutableLiveData<List<FavoriteUser>>()
    val listUser : LiveData<List<FavoriteUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        getAllFavs()
    }
    fun getAllFavs() {
        _isLoading.value = true
        userRepository.getAllFavs().observeForever { newListUsers ->
            _listUser.value = newListUsers
            _isLoading.value = false
        }
    }
    fun checkFavorite(username : String) = userRepository.checkFavorite(username)
    fun addFavorite(user: UserDetailResponse) {
        val favoriteUser = FavoriteUser(user.login, user.avatarUrl)
        userRepository.insert(favoriteUser)
    }

    fun deleteFavorite(user: UserDetailResponse) {
        val favoriteUser = FavoriteUser(user.login, user.avatarUrl)
        userRepository.delete(favoriteUser)
    }

}