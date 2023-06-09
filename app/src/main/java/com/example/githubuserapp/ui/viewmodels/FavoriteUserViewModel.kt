package com.example.githubuserapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.FavoriteUserRepository
import com.example.githubuserapp.data.local.entity.FavoriteUser

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getUser(): LiveData<List<FavoriteUser>> = mUserRepository.getUser()

}