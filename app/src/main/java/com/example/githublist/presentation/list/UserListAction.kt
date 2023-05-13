package com.example.githublist.presentation.list

sealed class UserListAction {
    class Navigate(val login: String) : UserListAction()
}