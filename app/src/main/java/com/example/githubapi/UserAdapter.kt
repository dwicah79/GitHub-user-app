package com.example.githubapi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubapi.data.response.ItemsItem
import com.example.githubapi.databinding.ItemRowBinding


class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((ItemsItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val user = getItem(position)
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra("USERNAME", user.login)

            val intent2 = Intent(holder.itemView.context, PagerAdapter::class.java)
            intent2.putExtra("USERNAME", user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        onItemClickListener = listener
    }

    inner class MyViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.loginTextView.text = user.login
            Glide.with(binding.avatarImageView).load(user.avatarUrl).transform(CircleCrop()).into(binding.avatarImageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
