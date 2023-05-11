package com.example.githublist.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.githublist.model.Repository
import com.example.githublist.model.User
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubAPI) {

    fun getUsers(): Flow<PagingData<User>> {
        return Pager(
                config = PagingConfig(
                    initialLoadSize = 30,
                pageSize = NETWORK_PAGE_SIZE,
                    prefetchDistance = 10,
                enablePlaceholders = false
             ),
                pagingSourceFactory = { GithubPagingSource(service) }
        ).flow
    }

    fun getRepo(username: String): LiveData<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 30,
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepositoryPagingSource(service, username) }
        ).liveData
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}