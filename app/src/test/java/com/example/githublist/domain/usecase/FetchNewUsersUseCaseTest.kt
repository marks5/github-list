package com.example.githublist.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.domain.UserRepository
import com.example.githublist.domain.useCase.FetchNewUsersUseCase
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
class FetchNewUsersUseCaseTest {

    private val mockUserRepository: UserRepository = mockk()

    private lateinit var fetchNewUsersUseCase: FetchNewUsersUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        fetchNewUsersUseCase = FetchNewUsersUseCase(
            mockUserRepository
        )
    }

    @Test
    fun `when fetch new users use case is executed then users are fetched in the repository`()
            = runTest {
        //given
        coEvery { mockUserRepository.fetchNewUsers() } coAnswers { }

        // when
        fetchNewUsersUseCase.execute()
        advanceUntilIdle()

        // then
        coVerify { mockUserRepository.fetchNewUsers() }
    }
}