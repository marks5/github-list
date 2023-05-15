package com.example.githublist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.model.RepoEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoriesRepositoryTest {

    private val mockUserNetwork: GithubAPI = mockk()

    private lateinit var userRepository: RepositoriesRepositoryImpl

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        userRepository = RepositoriesRepositoryImpl(
            mockUserNetwork,
        )
    }

    @Test
    fun `when repositories are fetched then return repositories`()
    = runTest {
        // given
        val repoEntities = givenRepoEntityList()
        coEvery { mockUserNetwork.getUserRepos(givenLogin()) } coAnswers {repoEntities}

        // when
        userRepository.fetchNewRepositories(givenLogin())

        // then
        coVerify { mockUserNetwork.getUserRepos(givenLogin()) }
    }

    private fun givenRepoEntity() = RepoEntity(fullName = "fullname", htmlUrl = "htmlUrl")
    private fun givenRepoEntityList() = listOf(
        givenRepoEntity(), givenRepoEntity()
    )
    private fun givenLogin() = "login"
}