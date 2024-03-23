package com.dicoding.githubusers.ui.detailUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.R
import com.dicoding.githubusers.data.remote.response.FollowerResponseItem
import com.dicoding.githubusers.databinding.FragmentFollowBinding
import com.dicoding.githubusers.ui.ViewModelFactory


class FollowFragment : Fragment() {
    private lateinit var binding : FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_USERNAME = "ARG_USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val username = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_POSITION, 0)

        if (savedInstanceState == null) {
            when (index) {
                0, 1 -> detailViewModel.getFollower(username.toString())
                2 -> detailViewModel.getFollowing(username.toString())
            }
        }

        detailViewModel.listFollower.observe(viewLifecycleOwner) { followers ->
            setFollowersData(followers)
        }

        detailViewModel.listFollowing.observe(viewLifecycleOwner) { following ->
            setFollowingData(following)
        }

        detailViewModel.isFollowLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowersData(followers: List<FollowerResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(followers)
        binding.rvUser.adapter = adapter
    }

    private fun setFollowingData(following: List<FollowerResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(following)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.followProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}