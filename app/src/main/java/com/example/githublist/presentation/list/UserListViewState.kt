package com.example.githublist.presentation.list

sealed class UserListViewState {
    object Loading : UserListViewState()
    class Results(val users: List<UserView>) : UserListViewState()
    class Error(val errorMessage: String?) : UserListViewState()
}