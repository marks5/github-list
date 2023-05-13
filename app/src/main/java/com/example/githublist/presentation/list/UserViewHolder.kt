package com.example.githublist.presentation.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.githublist.R
import com.example.githublist.databinding.UserViewItemBinding
import com.example.githublist.domain.model.UserDomain
import com.example.githublist.presentation.repo.RepositoriesActivity
import com.example.githublist.presentation.repo.RepositoriesActivity.Companion.EXTRA_USER

class UserViewHolder(
    root: View,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(root) {

    private val userViewBinding = UserViewItemBinding.bind(root)

    fun bindData(userView: UserView) {
        with(userViewBinding) {
            userLogin.text = userView.login
            root.setOnClickListener{ onItemClickListener.onItemClick(userView) }
            Glide.with(root.context).load(userView.avatarUrl).into(img)
        }
    }
}
