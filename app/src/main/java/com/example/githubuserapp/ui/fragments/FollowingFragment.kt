package com.example.githubuserapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.ui.viewmodels.FollowingViewModel
import com.example.githubuserapp.data.remote.response.GithubItemUser
import com.example.githubuserapp.ui.adapter.ListFollowingAdapter
import com.example.githubuserapp.ui.activities.UserDetailActivity
import com.example.githubuserapp.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private val followingViewModel by viewModels<FollowingViewModel>()

    companion object {
        const val TAG = "FragmentFollowing"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel.findFollowing(
            arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString()
        )

        followingViewModel.following.observe(viewLifecycleOwner) { following ->
            setFollowingList(following)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowingList(following: List<GithubItemUser>) {
        val users = ArrayList<GithubItemUser>()
        for (i in following) {
            users.clear()
            users.addAll(following)
        }
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ListFollowingAdapter(users)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}