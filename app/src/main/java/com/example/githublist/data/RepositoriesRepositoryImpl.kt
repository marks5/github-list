package com.example.githublist.data

import com.example.githublist.data.model.RepoEntity
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.RepositoriesRepository
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class RepositoriesRepositoryImpl @Inject constructor(
    private val userNetwork: GithubAPI,
): RepositoriesRepository {
    override suspend fun fetchNewRepositories(login: String) : List<RepoEntity> {
        try {
            return withTimeout(5000) {
                userNetwork.getUserRepos(login)
            }
        } catch (error: Exception) {
            throw UserFetchError()
        }
    }
}