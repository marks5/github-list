package com.example.githublist.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.domain.UserRepository
import com.example.githublist.domain.model.UserDomain
import com.example.githublist.domain.useCase.FilterLocalUsersUseCase
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
class FilterLocalUsersUseCaseTest {

    private val mockRepository: UserRepository = mockk()

    private lateinit var useCase: FilterLocalUsersUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        useCase = FilterLocalUsersUseCase(
            mockRepository
        )
    }

    @Test
    fun `when fetch new users with filter use case is executed then users are fetched in the repository`()
            = runTest {
        //given
        coEvery { mockRepository.queryUsersWithFilter(givenUserName()) } coAnswers { listOf(userDomain(), userDomain()) }

        // when
        useCase.execute(givenUserName())
        advanceUntilIdle()

        // then
        coVerify { mockRepository.queryUsersWithFilter(givenUserName()) }
    }

    private fun givenUserName() = "mark"
    private fun userDomain() = UserDomain(
        htmlUrl = givenUserName(),
        login = givenUserName()
    )
}