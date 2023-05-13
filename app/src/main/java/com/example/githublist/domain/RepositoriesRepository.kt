package com.example.githublist.domain

import com.example.githublist.data.model.RepoEntity

interface RepositoriesRepository {
    suspend fun fetchNewRepositories(login: String) : List<RepoEntity>
}