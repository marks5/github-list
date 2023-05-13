package com.example.githublist.domain.useCase

import com.example.githublist.domain.UserRepository
import javax.inject.Inject

class FilterLocalUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun execute(filter: String) = userRepository.queryUsersWithFilter(filter)
}