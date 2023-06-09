package com.example.githubuserapp.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.ui.viewmodels.MainViewModel
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.SettingPreferences
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.adapter.ListUserAdapter
import com.example.githubuserapp.ui.viewmodels.PreferencesViewModel
import com.example.githubuserapp.ui.viewmodels.PreferencesViewModelFactory
import com.google.android.material.snackbar.Snackbar

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        const val TAG = "MainActivity"
        const val GITHUB_USERNAME = "arif"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(datastore)
        val preferenceViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory(pref)
        )[PreferencesViewModel::class.java]
        preferenceViewModel.getThemeSetting().observe(this) { darkMode: Boolean ->
            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackBarText.observe(this) {
            Snackbar.make(
                window.decorView.rootView,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val favoriteIntent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(favoriteIntent)
                return true
            }
            R.id.menu_preferences -> {
                val preferenceIntent = Intent(this, PreferencesActivity::class.java)
                startActivity(preferenceIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserData(githubUsername: List<GithubItemUser>) {
        val listUser = ArrayList<GithubItemUser>()
        for (i in githubUsername) {
            listUser.clear()
            listUser.addAll(githubUsername)
        }
        val adapter = ListUserAdapter(listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object
            : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubItemUser) {
                moveToDetail(data)
            }
        })
    }

    private fun moveToDetail(data: GithubItemUser) {
        val detailUserIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
        detailUserIntent.putExtra(UserDetailActivity.EXTRA_USER, data.login)
        detailUserIntent.putExtra(UserDetailActivity.EXTRA_ACTIONBAR, data.login)
        startActivity(detailUserIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}