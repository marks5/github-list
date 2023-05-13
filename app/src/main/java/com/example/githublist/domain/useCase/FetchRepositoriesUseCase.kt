package com.example.githublist.domain.useCase

import com.example.githublist.domain.RepositoriesRepository
import com.example.githublist.domain.UserRepository
import javax.inject.Inject

class FetchRepositoriesUseCase @Inject constructor(
    private val userRepository: RepositoriesRepository
) {
    suspend fun execute(login: String) = userRepository.fetchNewRepositories(login)
}