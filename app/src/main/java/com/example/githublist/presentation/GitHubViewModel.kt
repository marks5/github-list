package com.example.githublist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.githublist.data.GithubRepository

class GitHubViewModel(
    repository: GithubRepository
) : ViewModel() {
    val users = repository.getUsers().cachedIn(viewModelScope)

//    private val searchQuery = MutableStateFlow("")
//
//    private val usersFlow = searchQuery.flatMapLatest { query ->
//        repository.searchUsers(if (query.isEmpty()) null else query)
//            .filter { it.login.contains(query, ignoreCase = true) }
//    }
//
//    val users: StateFlow<PagingData<User>> = combine(
//        usersFlow,
//        searchQuery
//    ) { users, query ->
//        if (query.isEmpty()) users
//        else users.filter { it.login.contains(query, ignoreCase = true) }
//    }.cachedIn(viewModelScope)
//
//    fun searchUsers(query: String) {
//        searchQuery.value = query
//    }
}