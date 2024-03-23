package com.dicoding.githubusers.di

import android.content.Context
import com.dicoding.githubusers.data.local.UserRepository
import com.dicoding.githubusers.data.local.datastore.SettingPreferences
import com.dicoding.githubusers.data.local.datastore.dataStore
import com.dicoding.githubusers.data.local.room.FavoriteUserDatabase
import com.dicoding.githubusers.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = SettingPreferences.getInstance(context.dataStore)
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(pref, dao, appExecutors)
    }
}