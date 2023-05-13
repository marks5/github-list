package com.example.githublist.data.mapper

import com.example.githublist.data.User
import com.example.githublist.domain.model.UserDomain
import javax.inject.Inject

class UserDaoToUserDomainMapper @Inject constructor() {

    fun userDaoToUserDomain(userDomainDao: User) = UserDomain(
        avatarUrl = userDomainDao.avatarUrl,
        login = userDomainDao.login
    )

}