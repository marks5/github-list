package com.example.githublist

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.githublist.data.GithubAPI
import com.example.githublist.data.GithubRepository
import com.example.githublist.presentation.ViewModelFactory

object Injection {

    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GithubAPI.create())
    }

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository())
    }
}
