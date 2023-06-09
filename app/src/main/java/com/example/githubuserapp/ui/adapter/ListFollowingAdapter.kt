package com.example.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.databinding.ItemRowUserBinding

class ListFollowingAdapter(private val listUser: List<GithubItemUser>) :
    RecyclerView.Adapter<ListFollowingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
        viewHolder.binding.tvItemName.text = user.login
        Glide.with(viewHolder.itemView.context)
            .load(user.avatarUrl)
            .into(viewHolder.binding.imgItemPhoto)
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)
}