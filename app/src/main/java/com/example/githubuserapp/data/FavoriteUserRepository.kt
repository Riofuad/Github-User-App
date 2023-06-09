package com.example.githubuserapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.local.room.FavoriteUserDao
import com.example.githubuserapp.data.local.room.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getInstance(application)
        mUserDao = db.favoriteUserDao()
    }

    fun getUser(): LiveData<List<FavoriteUser>> = mUserDao.getUser()

    fun getFavoriteUser(username: String): LiveData<FavoriteUser> =
        mUserDao.getFavoriteUser(username)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: String) {
        executorService.execute { mUserDao.deleteFavoriteUser(favoriteUser) }
    }
}