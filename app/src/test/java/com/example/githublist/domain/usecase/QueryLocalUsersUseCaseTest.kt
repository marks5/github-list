package com.example.githublist.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.domain.UserRepository
import com.example.githublist.domain.model.UserDomain
import com.example.githublist.domain.useCase.QueryLocalUsersUseCase
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
class QueryLocalUsersUseCaseTest {

    private val mockRepository: UserRepository = mockk()

    private lateinit var useCase: QueryLocalUsersUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        useCase = QueryLocalUsersUseCase(
            mockRepository
        )
    }

    @Test
    fun `when fetch new repositories use case is executed then repositories are fetched in the repository`()
            = runTest {
        //given
        coEvery { mockRepository.queryLocalUsers() } coAnswers { listOf(userDomain(), userDomain()) }

        // when
        useCase.execute()
        advanceUntilIdle()

        // then
        coVerify { mockRepository.queryLocalUsers() }
    }

    private fun givenUserName() = "mark"
    private fun userDomain() = UserDomain(
        htmlUrl = givenUserName(),
        login = givenUserName()
    )
}