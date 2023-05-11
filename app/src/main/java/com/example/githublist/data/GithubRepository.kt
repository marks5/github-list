package com.example.githublist.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githublist.model.User
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubAPI) {

    fun getUsers(): Flow<PagingData<User>> {
        return Pager(
                config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
             ),
                pagingSourceFactory = { GithubPagingSource(service) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}