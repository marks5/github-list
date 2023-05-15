package com.example.githublist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githublist.MainCoroutineRule
import com.example.githublist.data.model.RepoEntity
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.useCase.FetchRepositoriesUseCase
import com.example.githublist.presentation.custom.StringResources
import com.example.githublist.presentation.repo.RepoDomainToRepoViewMapper
import com.example.githublist.presentation.repo.RepoView
import com.example.githublist.presentation.repo.RepoViewModel
import com.example.githublist.presentation.repo.RepositoriesViewState
import io.mockk.*
import io.mockk.InternalPlatformDsl.toArray
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepoViewModelTest {

    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase = mockk()

    private val repoViewMapper: RepoDomainToRepoViewMapper = mockk()

    private val mockStringResources: StringResources = mockk()

    private lateinit var detailViewModel: RepoViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        detailViewModel = RepoViewModel(
            fetchRepositories = fetchRepositoriesUseCase,
            repoDomainToViewMapper = repoViewMapper,
            coroutineDispatcher = StandardTestDispatcher(),
            stringResources = mockStringResources
        )
    }

    @Test
    fun `when loading repository then repository is fetched, view state is results`()
            = runTest {
        coEvery { fetchRepositoriesUseCase.execute(givenUserName()) } coAnswers { listOf(repoEntity(), repoEntity())  }
        coEvery { repoViewMapper.userDomainToUserViewMapper(repoEntity()) } coAnswers {repoView()}

        // when
        detailViewModel.setUsername(givenUserName())
        advanceUntilIdle()

        // then
        coVerify{
            fetchRepositoriesUseCase.execute(givenUserName())
        }
        assert(detailViewModel.repoStateLiveData.value is RepositoriesViewState.Results)
    }

    @Test
    fun `when error occurs during repository load then view state is error`()
            = runTest {
        // given
        coEvery { fetchRepositoriesUseCase.execute(givenUserName()) }.throws(UserFetchError())
        coEvery { mockStringResources.getUserFetchErrorMessage() } coAnswers { "" }
        // when
        detailViewModel.setUsername(givenUserName())
        advanceUntilIdle()

        // then
        assert(detailViewModel.repoStateLiveData.value is RepositoriesViewState.Error)
    }


    private fun givenUserName() = "mark"
    private fun repoEntity() = RepoEntity(
        fullName = givenUserName(),
        htmlUrl = givenUserName()
    )
    private fun repoView() = RepoView(
        fullName = givenUserName(),
        htmlUrl = givenUserName()
    )
}