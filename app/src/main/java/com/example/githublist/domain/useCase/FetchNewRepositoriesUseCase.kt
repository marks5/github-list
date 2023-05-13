package com.example.githublist.domain.useCase

import com.example.githublist.data.model.RepoEntity
import com.example.githublist.domain.RepositoriesRepository
import javax.inject.Inject

class FetchNewRepositoriesUseCase @Inject constructor(
    private val repoRepository: RepositoriesRepository
) {
    suspend fun execute(login: String) : List<RepoEntity> = repoRepository.fetchNewRepositories(login)
}