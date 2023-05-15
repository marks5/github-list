package com.example.githublist.presentation.repo

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.githublist.databinding.RepoViewItemBinding

class RepositoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val userViewBinding = RepoViewItemBinding.bind(view)

    fun bindData(userView: RepoView) {
        with(userViewBinding) {
            repo.text = userView.fullName
        }
    }
}
