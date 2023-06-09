package com.example.githubuserapp.data.remote.retrofit

import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.data.remote.response.GithubResponse
import com.example.githubuserapp.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun searchUser(@Query("q") login: String?): Call<GithubResponse>

    @GET("/users/{login}")
    fun getDetailUser(@Path("login") login: String?): Call<UserDetailResponse>

    @GET("/users/{login}/followers")
    fun getFollowers(@Path("login") login: String?): Call<List<GithubItemUser>>

    @GET("/users/{login}/following")
    fun getFollowing(@Path("login") login: String?): Call<List<GithubItemUser>>
}