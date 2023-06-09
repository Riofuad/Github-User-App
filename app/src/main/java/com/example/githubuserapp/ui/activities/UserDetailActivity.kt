package com.example.githubuserapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.remote.response.UserDetailResponse
import com.example.githubuserapp.ui.viewmodels.UserDetailViewModel
import com.example.githubuserapp.databinding.ActivityUserDetailBinding
import com.example.githubuserapp.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapp.ui.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var actionBar: ActionBar
    private val userDetailViewModel by viewModels<UserDetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    companion object {
        const val EXTRA_ACTIONBAR = "extra_actionBar"
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"

        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra(EXTRA_ACTIONBAR).toString()
        actionBar.setDisplayHomeAsUpEnabled(true)

        userDetailViewModel.detail.observe(this) { userDetail ->
            setUserData(userDetail)
            setUpFavoriteUser(userDetail)
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userDetailViewModel.snackBarText.observe(this) {
            Snackbar.make(
                window.decorView.rootView,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        setFragment()

    }

    private fun setUpFavoriteUser(userDetail: UserDetailResponse) {
        userDetailViewModel.getFavoriteUser(userDetail.login ?: "").observe(this) {
            val favoriteUser = FavoriteUser(userDetail.login ?: "", userDetail.avatarUrl)
            var isFavorite = false

            if (it != null) {
                isFavorite = true
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24dp)
            }

            binding.fabFavorite.setOnClickListener {
                if (!isFavorite) {
                    userDetailViewModel.insert(favoriteUser)
                    isFavorite = true
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24dp)
                } else {
                    userDetailViewModel.delete(favoriteUser.username)
                    isFavorite = false
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border_24dp)
                }
            }
        }
    }

    private fun setFragment() {
        val userIntent = intent.extras
        if (userIntent != null) {
            val userLogin = userIntent.getString(EXTRA_USER)
            userDetailViewModel.getDetail(userLogin!!)

            val login = Bundle()
            login.putString(EXTRA_FRAGMENT, userLogin)

            val sectionsPagerAdapter = SectionsPagerAdapter(this, login)

            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }

    private fun setUserData(username: UserDetailResponse) {
        binding.apply {
            Glide.with(this@UserDetailActivity)
                .load(username.avatarUrl)
                .into(ivDetailPhoto)
            tvDetailUsername.text = username.login
            if (username.name.toString() != "null")
                tvDetailName.text = username.name.toString()
            tvFollowers.text = getString(R.string.followers, username.followers.toString())
            tvFollowing.text = getString(R.string.following, username.following.toString())
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_share -> {
                userDetailViewModel.detail.observe(this) { userDetail ->
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Hey, check out this user! \n${userDetail.htmlUrl}"
                    )
                    shareIntent.type = "text/plain"
                    startActivity(Intent.createChooser(shareIntent, "Share to: "))
                }
            }
        }
        return super.onContextItemSelected(item)
    }
}