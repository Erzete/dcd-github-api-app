package com.dicoding.githubusers.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.githubusers.data.local.datastore.SettingPreferences
import com.dicoding.githubusers.data.local.entity.FavoriteUser
import com.dicoding.githubusers.data.local.room.FavoriteUserDao
import com.dicoding.githubusers.utils.AppExecutors

class UserRepository private constructor(
    private val pref: SettingPreferences,
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
) {

    fun getAllFavs(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getAllFavs()
    }

    fun checkFavorite(username : String): LiveData<FavoriteUser> {
        return favoriteUserDao.getFavoriteUserByUsername(username)
    }

    fun insert(favUser: FavoriteUser) {
        appExecutors.diskIO.execute { favoriteUserDao.insert(favUser) }
    }
    fun delete(favUser: FavoriteUser) {
        appExecutors.diskIO.execute { favoriteUserDao.delete(favUser) }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            pref: SettingPreferences,
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(pref, favoriteUserDao, appExecutors)
            }.also { instance = it }
    }
}