package com.example.githublist.presentation.repo

sealed class RepositoriesViewState {
    object Loading : RepositoriesViewState()
    class Results(val repos: List<RepoView>) : RepositoriesViewState()
    class Error(val errorMessage: String?) : RepositoriesViewState()
}