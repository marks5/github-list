package com.example.githublist.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.githublist.R

class UserAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<UserView, UserViewHolder>(UserCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_view_item, parent, false)
        return UserViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

    override fun getItemCount() = currentList.size
}

private class UserCallback : DiffUtil.ItemCallback<UserView>() {
    override fun areItemsTheSame(oldItem: UserView, newItem: UserView): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: UserView, newItem: UserView): Boolean {
        return oldItem == newItem
    }
}

interface OnItemClickListener {
    fun onItemClick(user: UserView)
}