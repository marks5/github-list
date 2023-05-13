package com.example.githublist.domain.useCase

import com.example.githublist.domain.UserRepository
import javax.inject.Inject

class QueryLocalUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute() = userRepository.queryLocalUsers()
}