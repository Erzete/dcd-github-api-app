package com.dicoding.githubusers.data.retrofit

import com.dicoding.githubusers.BuildConfig
import com.dicoding.githubusers.data.response.FollowerResponseItem
import com.dicoding.githubusers.data.response.UserDetailResponse
import com.dicoding.githubusers.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token " + BuildConfig.TOKEN)
    @GET("search/users")
    fun getListUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @Headers("Authorization: token " + BuildConfig.TOKEN)
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @Headers("Authorization: token " + BuildConfig.TOKEN)
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<FollowerResponseItem>>

    @Headers("Authorization: token " + BuildConfig.TOKEN)
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<FollowerResponseItem>>
}