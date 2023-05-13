package com.example.githublist.presentation.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.githublist.R

class RepoAdapter :
    ListAdapter<RepoView, RepoViewHolder>(UserCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_view_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

    override fun getItemCount() = currentList.size
}

private class UserCallback : DiffUtil.ItemCallback<RepoView>() {
    override fun areItemsTheSame(oldItem: RepoView, newItem: RepoView): Boolean {
        return oldItem.fullName == newItem.fullName
    }

    override fun areContentsTheSame(oldItem: RepoView, newItem: RepoView): Boolean {
        return oldItem == newItem
    }
}