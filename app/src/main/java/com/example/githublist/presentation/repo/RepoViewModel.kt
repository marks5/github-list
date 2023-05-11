package com.example.githublist.presentation.repo

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githublist.data.GithubRepository
import com.example.githublist.model.Repository

class RepoViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val repoUsernameLiveData = MutableLiveData<String>()

    fun getRepos(): LiveData<PagingData<Repository>> =
        repoUsernameLiveData.switchMap { username ->
            repository.getRepo(username).cachedIn(viewModelScope)
        }

    fun setUsername(username: String) {
        this.repoUsernameLiveData.value = username
    }
}