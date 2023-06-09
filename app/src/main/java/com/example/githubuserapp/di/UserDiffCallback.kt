package com.example.githubuserapp.di

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.data.local.entity.FavoriteUser

class UserDiffCallback(
    private val mOldUserList: List<FavoriteUser>,
    private val mNewUserList: List<FavoriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldUserList.size

    override fun getNewListSize(): Int = mNewUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].username == mNewUserList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mOldUserList[newItemPosition]
        return oldEmployee.avatarUrl == newEmployee.avatarUrl
    }
}