package com.example.githublist.data.mapper

import com.example.githublist.data.User
import com.example.githublist.data.model.UserEntity
import com.example.githublist.domain.model.UserDomain
import javax.inject.Inject

class UserEntityToDaoMapper @Inject constructor() {
    fun userEntityToDao(userEntity: UserEntity) = User(
        login = userEntity.login,
        avatarUrl = userEntity.avatarUrl
    )
}