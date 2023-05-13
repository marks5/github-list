package com.example.githublist.domain

import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.domain.model.UserDomain

interface UserRepository {
    suspend fun deleteUser(login: String)
    suspend fun fetchNewUsers()
    suspend fun fetchUser(login: String) : UserDetailEntity
    fun queryUsersWithFilter(filter: String): List<UserDomain>?
    suspend fun queryLocalUsers(): List<UserDomain>?
}