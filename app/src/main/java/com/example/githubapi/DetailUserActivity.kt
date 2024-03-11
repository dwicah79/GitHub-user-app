package com.example.githubapi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubapi.databinding.ActivityDetailUserBinding
import com.example.githubapi.ui.model.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private var isFavorite: Boolean = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val detailUserModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        supportActionBar?.hide()

        val username = intent.getStringExtra("USERNAME")
        val avatarUrl = intent.getStringExtra("AVATAR_URL")
        if (username != null) {
            detailUserModel.getDetailUser(username)
            detailUserModel.getFollowers(username)
            detailUserModel.getFollowing(username)
        }

        detailUserModel.isLoading.observe(this){
            showLoading(it)
        }

        detailUserModel.detailUser.observe(this) { detailUser ->
            val followers = detailUser.followers
            val following = detailUser.following

            binding.tvname.text = detailUser.name
            binding.tvusername.text = detailUser.login
            binding.jumfollow.text = "$followers"+"\n"+"Followers"
            binding.jumfollowing.text = "$following"+"\n"+"Following"
            Glide.with(binding.avatarImageView)
                .load(detailUser.avatarUrl)
                .transform(CircleCrop())
                .into(binding.avatarImageView)
        }

        val sectionsPagerAdapter = PagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
