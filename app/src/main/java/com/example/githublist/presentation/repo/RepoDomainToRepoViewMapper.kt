package com.example.githublist.presentation.repo

import com.example.githublist.data.model.RepoEntity
import javax.inject.Inject

class RepoDomainToRepoViewMapper @Inject constructor() {

    fun userDomainToUserViewMapper(user: RepoEntity) = RepoView(
        fullName = user.fullName,
        htmlUrl = user.htmlUrl
    )
}