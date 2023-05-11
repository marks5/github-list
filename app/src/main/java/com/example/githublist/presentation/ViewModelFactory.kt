package com.example.githublist.presentation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.githublist.data.GithubRepository

class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: GithubRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GitHubViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
