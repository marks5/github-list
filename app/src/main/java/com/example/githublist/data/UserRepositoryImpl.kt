package com.example.githublist.data

import com.example.githublist.data.mapper.UserDaoToUserDomainMapper
import com.example.githublist.data.mapper.UserEntityToDaoMapper
import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.UserRepository
import com.example.githublist.domain.model.UserDomain
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userNetwork: GithubAPI,
    private val userDao: UserDao,
    private val userDataStore: UserDataStore,
    private val userEntityToDaoMapper: UserEntityToDaoMapper,
    private val userDaoToUserDomainMapper: UserDaoToUserDomainMapper
): UserRepository {
    override suspend fun fetchNewUsers() {
        try {
            val newUsers = withTimeout(5000) {
                userNetwork.getUsers(1,200)
            }.map {
                userEntityToDaoMapper.userEntityToDao(it)
            }
            val deletedUsers = userDataStore.deletedUsersFlow.first()
            val filteredUsersList = newUsers.filterNot { user ->
                deletedUsers.contains(user.login)
            }
            userDao.insertUsers(filteredUsersList)
        } catch (error: Exception) {
            throw UserFetchError()
        }
    }

    override suspend fun fetchUser(login: String): UserDetailEntity {
        try {
            return withTimeout(5000) {
                userNetwork.searchUser(login)
            }
        } catch (error: java.lang.Exception) {
            throw UserFetchError()
        }
    }

    override suspend fun queryLocalUsers() = userDao.queryAllUsers()?.map {
        userDaoToUserDomainMapper.userDaoToUserDomain(it)
    }

    override suspend fun deleteUser(login: String) {
        userDao.deleteUser(login)
        userDataStore.updateDeletedUsers(login)
    }

    override fun queryUsersWithFilter(filter: String): List<UserDomain>? =
        userDao.queryUsersWithFilter(filter)?.map {
            userDaoToUserDomainMapper.userDaoToUserDomain(it)
        }
}