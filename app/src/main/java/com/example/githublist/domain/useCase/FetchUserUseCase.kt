package com.example.githublist.domain.useCase

import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.domain.UserRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(login: String) : UserDetailEntity = userRepository.fetchUser(login)
}