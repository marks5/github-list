package com.example.githublist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.useCase.FetchUserUseCase
import com.example.githublist.presentation.custom.StringResources
import com.example.githublist.presentation.detail.DetailUserView
import com.example.githublist.presentation.detail.DetailViewModel
import com.example.githublist.presentation.detail.DetailViewState
import com.example.githublist.presentation.detail.UserDetailDomainToUserDetailViewMapper
import com.example.githublist.presentation.list.*
import com.google.common.base.CharMatcher.any
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private val fetchUserUseCase: FetchUserUseCase = mockk()

    private val userDe: UserDetailDomainToUserDetailViewMapper = mockk()

    private val mockStringResources: StringResources = mockk()

    private lateinit var detailViewModel: DetailViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(
            fetchUser = fetchUserUseCase,
            userDomainToViewMapper = userDe,
            coroutineDispatcher = StandardTestDispatcher(),
            stringResources = mockStringResources
        )
    }

    @Test
    fun `when loading user then user is fetched, view state is results`()
            = runTest {
        coEvery { fetchUserUseCase.execute(givenUserName()) } coAnswers { userDetailEntity() }
        coEvery { userDe.userDomainToUserViewMapper(userDetailEntity()) } coAnswers {detailUserView()}

        // when
        detailViewModel.setUsername(givenUserName())
        advanceUntilIdle()

        // then
        coVerify{
            fetchUserUseCase.execute(givenUserName())
        }
        assert(detailViewModel.userStateLiveData.value is DetailViewState.Results)
    }

    @Test
    fun `when error occurs during users load then view state is error`()
            = runTest {
        // given
        coEvery { fetchUserUseCase.execute(givenUserName()) }.throws(UserFetchError())
        coEvery { mockStringResources.getUserFetchErrorMessage() } coAnswers { "" }
        // when
        detailViewModel.setUsername(givenUserName())
        advanceUntilIdle()

        // then
        assert(detailViewModel.userStateLiveData.value is DetailViewState.Error)
    }


    private fun givenUserName() = "mark"
    private fun userDetailEntity() = UserDetailEntity(
        login = givenUserName(),
        name = givenUserName()
    )
    private fun detailUserView() = DetailUserView(
        login = givenUserName(),
        name = givenUserName(),
        bio = "bio",
        location = "location",
        email = "email@email.com",
        company = "company",
        avatarUrl = "avatarUrl",
        blog = "blog"
    )
}