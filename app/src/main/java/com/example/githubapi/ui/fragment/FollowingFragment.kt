package com.example.githubapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.UserAdapter
import com.example.githubapi.data.response.ItemsItem
import com.example.githubapi.databinding.FragmentFollowingBinding
import com.example.githubapi.ui.model.DetailUserViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION) ?: 0
        val username = arguments?.getString(ARG_USERNAME)

        val viewModel = ViewModelProvider(requireActivity()).get(DetailUserViewModel::class.java)
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())

        if (position == 1) {
            username?.let { viewModel.getFollowers(it) }
            viewModel.followers.observe(viewLifecycleOwner) { followers ->
                viewModel.isLoading.observe(viewLifecycleOwner){
                    showLoading(it)
                }
                setUserFollow(followers)
            }
        } else {
            username?.let { viewModel.getFollowing(it) }
            viewModel.following.observe(viewLifecycleOwner) { following ->
                viewModel.isLoading.observe(viewLifecycleOwner){
                    showLoading(it)
                }
                setUserFollow(following)
            }
        }
    }

    private fun setUserFollow(userFollow: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userFollow)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}
