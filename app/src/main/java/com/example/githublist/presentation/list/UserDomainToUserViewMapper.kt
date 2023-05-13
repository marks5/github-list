package com.example.githublist.presentation.list

import com.example.githublist.domain.model.UserDomain
import javax.inject.Inject

class UserDomainToUserViewMapper @Inject constructor() {

    fun userDomainToUserViewMapper(user: UserDomain) = UserView(
        login = user.login.orEmpty(),
        avatarUrl = user.avatarUrl.orEmpty()
    )
}