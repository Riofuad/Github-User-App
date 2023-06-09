package com.example.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favoriteUser ORDER BY login ASC")
    fun getUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favoriteUser WHERE login = :favoriteUser")
    fun getFavoriteUser(favoriteUser: String): LiveData<FavoriteUser>

    @Query("DELETE FROM favoriteUser WHERE login = :favoriteUser")
    fun deleteFavoriteUser(favoriteUser: String)
}