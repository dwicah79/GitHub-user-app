package com.example.githubapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapi.ui.fragment.FollowingFragment

class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""
    override fun createFragment(position: Int): Fragment {

        val fragment = FollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingFragment.ARG_POSITION, position + 1)
            putString(FollowingFragment.ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount() = 2

}