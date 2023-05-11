package com.example.githublist.presentation.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githublist.R
import com.example.githublist.model.Repository

class RepositoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val fullName: TextView = view.findViewById(R.id.repo_name)

    private var user: Repository? = null

    fun bind(user: Repository?) {
        if (user == null) {
            val resources = itemView.resources
            fullName.text = resources.getString(R.string.unknow)
        } else {
            showRepoData(user)
        }
    }

    private fun showRepoData(user: Repository) {
        this.user = user
        fullName.text = user.fullName
    }

    companion object {
        fun create(parent: ViewGroup): RepositoriesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepositoriesViewHolder(view)
        }
    }
}
