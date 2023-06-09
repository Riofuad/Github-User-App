package com.example.githubuserapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.data.remote.response.GithubResponse
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.ui.activities.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<List<GithubItemUser>>()
    val user: LiveData<List<GithubItemUser>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    init {
        findUser(MainActivity.GITHUB_USERNAME)
    }

    internal fun findUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = response.body()?.items
                    }
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                    _snackBarText.value = "An error has occurred!"
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
                _snackBarText.value = "An error has occurred! Please try again later."
            }
        })
    }
}