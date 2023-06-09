package com.example.githubuserapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.ui.fragments.FollowersFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _followers = MutableLiveData<List<GithubItemUser>>()
    val followers: LiveData<List<GithubItemUser>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun findFollowers(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(login)
        client.enqueue(object : Callback<List<GithubItemUser>> {
            override fun onResponse(
                call: Call<List<GithubItemUser>>,
                response: Response<List<GithubItemUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = response.body()
                    }
                } else {
                    Log.e(FollowersFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubItemUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowersFragment.TAG, "onFailure: ${t.message}")
            }
        })
    }
}