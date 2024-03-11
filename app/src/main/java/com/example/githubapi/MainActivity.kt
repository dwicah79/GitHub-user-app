package com.example.githubapi
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Set up RecyclerView
        val adapter = UserAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe userList from ViewModel
        userViewModel.userList.observe(this, { userList ->
            binding.progressBar.visibility = View.GONE
            adapter.submitList(userList)
        })


        // Set click listener on RecyclerView items
        adapter.setOnItemClickListener { user ->
            val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
            intent.putExtra("USERNAME", user.login)
            intent.putExtra("AVATAR_URL", user.avatarUrl)
            startActivity(intent)
        }

        // Fetch initial user list
        userViewModel.searchUsers("arif")
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.progressBar.visibility = View.VISIBLE
                    userViewModel.searchUsers(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // You can perform actions while user is typing here
                return false
            }
        })

    }
}
