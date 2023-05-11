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
import com.example.githublist.model.User
import com.example.githublist.presentation.repo.RepositoriesActivity
import com.example.githublist.presentation.repo.RepositoriesActivity.Companion.EXTRA_USER

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val login: TextView = view.findViewById(R.id.user_login)
    private val img: ImageView = view.findViewById(R.id.img)

    private var user: User? = null

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, RepositoriesActivity::class.java).apply {
                putExtra(EXTRA_USER, user?.login)
            }
            view.context.startActivity(intent)
        }
    }

    fun bind(user: User?) {
        if (user == null) {
            val resources = itemView.resources
            login.text = resources.getString(R.string.unknow)
        } else {
            showRepoData(user)
        }
    }

    private fun showRepoData(user: User) {
        this.user = user
        login.text = user.login

        Glide.with(itemView.context)
            .load(user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(img)
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_view_item, parent, false)
            return UserViewHolder(view)
        }
    }
}
