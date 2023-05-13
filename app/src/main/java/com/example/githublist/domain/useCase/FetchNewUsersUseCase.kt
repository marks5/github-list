package com.example.githublist.domain.useCase

import com.example.githublist.domain.UserRepository
import javax.inject.Inject

class FetchNewUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute() = userRepository.fetchNewUsers()
}