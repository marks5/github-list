package com.example.githublist.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.domain.UserRepository
import com.example.githublist.domain.model.UserDomain
import com.example.githublist.domain.useCase.FetchUserUseCase
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
class FetchUserUseCaseTest {

    private val mockRepository: UserRepository = mockk()

    private lateinit var useCase: FetchUserUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onSetup() {
        useCase = FetchUserUseCase(
            mockRepository
        )
    }

    @Test
    fun `when fetch user use case is executed then user is fetched in the repository`()
            = runTest {
        //given
        coEvery { mockRepository.fetchUser(givenUserName()) } coAnswers { userDomain() }

        // when
        useCase.execute(givenUserName())
        advanceUntilIdle()

        // then
        coVerify { mockRepository.fetchUser(givenUserName()) }
    }

    private fun givenUserName() = "mark"
    private fun userDomain() = UserDetailEntity(
        name = givenUserName(),
        login = givenUserName()
    )
}