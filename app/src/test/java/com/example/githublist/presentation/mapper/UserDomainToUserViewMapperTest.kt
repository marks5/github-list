package com.example.githublist.presentation.mapper

import com.example.githublist.domain.model.UserDomain
import com.example.githublist.presentation.list.UserDomainToUserViewMapper
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class UserDomainToUserViewMapperTest {

    private lateinit var userDomainToUserViewMapper: UserDomainToUserViewMapper

    @Before
    fun onSetup() {
        userDomainToUserViewMapper = UserDomainToUserViewMapper()
    }

    @Test
    fun `when user domain is received then it is mapped to an user view correctly`() {
        // given
        val login = "marksao"
        val url = "url1"

        val user = UserDomain(login = login, avatarUrl = url)

        // when
        val userView = userDomainToUserViewMapper.userDomainToUserViewMapper(user)

        // then
        with (userView) {
            assertEquals("marksao", this.login)
            assertEquals("url1", this.avatarUrl)
        }
    }
}