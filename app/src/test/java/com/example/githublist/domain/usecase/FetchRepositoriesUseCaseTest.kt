package com.example.githublist.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.model.RepoEntity
import com.example.githublist.domain.RepositoriesRepository
import com.example.githublist.domain.useCase.FetchNewUsersUseCase
import com.example.githublist.domain.useCase.FetchRepositoriesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchRepositoriesUseCaseTest {

    private val mockRepository: RepositoriesRepository = mockk()

    private lateinit var useCase: FetchRepositoriesUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        useCase = FetchRepositoriesUseCase(
            mockRepository
        )
    }

    @Test
    fun `when fetch new repositories use case is executed then repositories are fetched in the repository`()
            = runTest {
        //given
        coEvery { mockRepository.fetchNewRepositories(givenUserName()) } coAnswers { listOf(repoEntity(), repoEntity()) }

        // when
        useCase.execute(givenUserName())
        advanceUntilIdle()

        // then
        coVerify { mockRepository.fetchNewRepositories(givenUserName()) }
    }

    private fun givenUserName() = "mark"
    private fun repoEntity() = RepoEntity(
        fullName = givenUserName(),
        htmlUrl = givenUserName()
    )
}