package com.example.githubapi.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi.data.response.ItemsItem

class FollowViewModel: ViewModel() {

    private val _userListFollow = MutableLiveData<List<ItemsItem>?>()
    val userListFollow: MutableLiveData<List<ItemsItem>?> get() = _userListFollow

    fun setUserListFollow(responseBody: List<ItemsItem>?) {
        _userListFollow.value = responseBody
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }
    init {
        setLoading(true)
    }

}
