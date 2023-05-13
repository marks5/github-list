package com.example.githublist.presentation.repo

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.githublist.databinding.RepoViewItemBinding

class RepoViewHolder(
    root: View
) : RecyclerView.ViewHolder(root) {

    private val userViewBinding = RepoViewItemBinding.bind(root)

    fun bindData(userView: RepoView) {
        with(userViewBinding) {
            repo.text = userView.fullName
        }
    }
}
