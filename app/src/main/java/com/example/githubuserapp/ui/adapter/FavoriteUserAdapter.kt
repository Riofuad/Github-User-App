package com.example.githubuserapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.di.UserDiffCallback
import com.example.githubuserapp.ui.activities.UserDetailActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.MyViewHolder>() {
    private val listFavorites = ArrayList<FavoriteUser>()
    fun setListFavorites(favorite: List<FavoriteUser>) {
        val diffCallback = UserDiffCallback(this.listFavorites, favorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(favorite)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteUser) {
            with(binding) {
                tvItemName.text = user.username
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.EXTRA_USER, user.username)
                    intent.putExtra(UserDetailActivity.EXTRA_ACTIONBAR, user.username)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }

    override fun getItemCount(): Int = listFavorites.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favoriteUser = listFavorites[position]
        holder.bind(favoriteUser)
    }
}