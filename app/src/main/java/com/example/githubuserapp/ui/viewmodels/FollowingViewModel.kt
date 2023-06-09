package com.example.githubuserapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.ui.fragments.FollowingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _following = MutableLiveData<List<GithubItemUser>>()
    val following: LiveData<List<GithubItemUser>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun findFollowing(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<GithubItemUser>> {
            override fun onResponse(
                call: Call<List<GithubItemUser>>,
                response: Response<List<GithubItemUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = response.body()
                    }
                } else {
                    Log.e(FollowingFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubItemUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowingFragment.TAG, "onFailure: ${t.message}")
            }
        })
    }
}