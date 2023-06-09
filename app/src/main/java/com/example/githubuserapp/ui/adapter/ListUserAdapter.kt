package com.example.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<GithubItemUser>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubItemUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
        viewHolder.binding.tvItemName.text = user.login
        Glide.with(viewHolder.itemView.context)
            .load(user.avatarUrl)
            .into(viewHolder.binding.imgItemPhoto)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)
}