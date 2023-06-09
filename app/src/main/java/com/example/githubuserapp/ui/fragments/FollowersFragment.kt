package com.example.githubuserapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.ui.viewmodels.FollowersViewModel
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.ui.adapter.ListFollowersAdapter
import com.example.githubuserapp.ui.activities.UserDetailActivity
import com.example.githubuserapp.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel by viewModels<FollowersViewModel>()

    companion object {
        const val TAG = "FragmentFollowers"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followersViewModel.findFollowers(
            arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString()
        )

        followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
            setFollowersList(followers)
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowersList(followers: List<GithubItemUser>) {
        val users = ArrayList<GithubItemUser>()
        for (i in followers) {
            users.clear()
            users.addAll(followers)
        }
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ListFollowersAdapter(users)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}