package com.example.githublist.presentation.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githublist.R
import com.example.githublist.databinding.RepoViewItemBinding
import com.example.githublist.databinding.UserViewItemBinding
import com.example.githublist.domain.model.Repository
import com.example.githublist.presentation.list.UserView

class RepositoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val userViewBinding = RepoViewItemBinding.bind(view)

    fun bindData(userView: RepoView) {
        with(userViewBinding) {
            repo.text = userView.fullName
        }
    }
}
