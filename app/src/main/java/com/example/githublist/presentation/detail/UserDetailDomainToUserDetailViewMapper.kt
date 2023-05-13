package com.example.githublist.presentation.detail

import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.data.model.UserEntity
import com.example.githublist.domain.model.UserDomain
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class UserDetailDomainToUserDetailViewMapper @Inject constructor() {

    fun userDomainToUserViewMapper(user: UserDetailEntity) = DetailUserView(
        login = user.login.orEmpty(),
        avatarUrl = user.avatarUrl.orEmpty(),
        name = user.name.orEmpty(),
        company = user.company.orEmpty(),
        blog = user.blog.orEmpty(),
        location = user.location.orEmpty(),
        email = user.email.orEmpty(),
        bio = user.bio.orEmpty()
    )
}