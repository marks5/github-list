package com.example.githublist.mock

import android.content.Context
import com.example.githublist.data.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockUserDataStore(context: Context) : UserDataStore(context) {

    private val deletedUsers = mutableSetOf<String>()

    override val deletedUsersFlow: Flow<Set<String>>
        get() = flowOf(deletedUsers)

    override suspend fun updateDeletedUsers(login: String) {
        deletedUsers.add(login)
    }
}