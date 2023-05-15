package com.example.githublist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.useCase.FetchNewUsersUseCase
import com.example.githublist.domain.useCase.FilterLocalUsersUseCase
import com.example.githublist.domain.useCase.QueryLocalUsersUseCase
import com.example.githublist.presentation.custom.StringResources
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
class UserListViewModelTest {

    private val mockFetchNewUsersUseCase: FetchNewUsersUseCase = mockk()

    private val mockQueryLocalUsersUseCase: QueryLocalUsersUseCase = mockk()

    private val mockFilterLocalUsersUseCase: FilterLocalUsersUseCase = mockk()

    private val mockUserDomainToUserViewMapper: UserDomainToUserViewMapper = mockk()

    private val mockStringResources: StringResources = mockk()

    private lateinit var userListViewModel: UserListViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        userListViewModel = UserListViewModel(
            fetchNewUsersUseCase = mockFetchNewUsersUseCase,
            queryLocalUsersUseCase = mockQueryLocalUsersUseCase,
            filterLocalUsersUseCase = mockFilterLocalUsersUseCase,
            userDomainToUserViewMapper = mockUserDomainToUserViewMapper,
            coroutineDispatcher = StandardTestDispatcher(),
            stringResources = mockStringResources
        )
    }

    @Test
    fun `when loading users then new users are fetched, database is queried and view state is results`()
            = runTest {
        coEvery { mockFetchNewUsersUseCase.execute() } coAnswers { any() }
        coEvery { mockQueryLocalUsersUseCase.execute() } coAnswers { emptyList() }

        // when
        userListViewModel.loadUsers()
        advanceUntilIdle()

        // then
        coVerify{
            mockFetchNewUsersUseCase.execute()
            mockQueryLocalUsersUseCase.execute()
        }
        assert(userListViewModel.userListViewStateLiveData.value is UserListViewState.Results)
    }

    @Test
    fun `when error occurs during users load then view state is error`()
            = runTest {
        // given
        coEvery { mockFetchNewUsersUseCase.execute() }.throws(UserFetchError())
        coEvery { mockStringResources.getUserFetchErrorMessage() } coAnswers { "" }
        // when
        userListViewModel.loadUsers()
        advanceUntilIdle()

        // then
        assert(userListViewModel.userListViewStateLiveData.value is UserListViewState.Error)
    }

    @Test
    fun `when user is clicked then action is to navigate`() {
        // given
        val userView = givenUserView()

        // when
        userListViewModel.onUserClicked(userView)

        // then
        assert(userListViewModel.userListActionLiveData.value is UserListAction.Navigate)
    }

    @Test
    fun `when users are filtered then filter use case is executed and view state is results`() = runTest {
        // given
        val filter = "Mrk"
        coEvery { mockFetchNewUsersUseCase.execute() } coAnswers { any() }
        coEvery { mockQueryLocalUsersUseCase.execute() } coAnswers { emptyList() }
        coEvery { mockFilterLocalUsersUseCase.execute(filter) } coAnswers { emptyList() }

        // when
        userListViewModel.filterUsers(filter)
        advanceUntilIdle()

        // then
        assertEquals(filter, userListViewModel.filterApplied)
        coVerify {mockFilterLocalUsersUseCase.execute(filter)}
        assert(userListViewModel.userListViewStateLiveData.value is UserListViewState.Results)
    }

    private fun givenUserView() = UserView(
    "mark",
    "given.url")
}