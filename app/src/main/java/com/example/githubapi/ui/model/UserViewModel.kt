package com.example.githubapi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi.data.response.GithubSearchResponse
import com.example.githubapi.data.response.ItemsItem
import com.example.githubapi.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> get() = _userList

    fun searchUsers(query: String) {
        val client = ApiClient.apiService.getUsers(query)
        client.enqueue(object : Callback<GithubSearchResponse> {
            override fun onResponse(
                call: Call<GithubSearchResponse>,
                response: Response<GithubSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()?.items?.filterNotNull() ?: emptyList()
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<GithubSearchResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
