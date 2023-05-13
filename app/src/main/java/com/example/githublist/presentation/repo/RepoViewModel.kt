package com.example.githublist.presentation.repo

import androidx.lifecycle.*
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.useCase.FetchRepositoriesUseCase
import com.example.githublist.presentation.custom.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val fetchRepositories: FetchRepositoriesUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val stringResources: StringResources,
    private val repoDomainToViewMapper: RepoDomainToRepoViewMapper,
    ) : ViewModel() {

    private val repoState: MutableLiveData<RepositoriesViewState> = MutableLiveData()
    val repoStateLiveData: LiveData<RepositoriesViewState> = repoState

    fun setUsername(login: String) {
        this.repoState.value = RepositoriesViewState.Loading

        viewModelScope.launch {
            try {
                withContext(coroutineDispatcher) {
                    repoState.postValue(RepositoriesViewState.Results(getRepos(login)))
                }
            } catch (error: UserFetchError) {
                repoState.value = RepositoriesViewState.Error(stringResources.getUserFetchErrorMessage())
            }
        }
    }

    private suspend fun getRepos(login: String) = withContext(coroutineDispatcher) {
        fetchRepositories.execute(login).map {
            repoDomainToViewMapper.userDomainToUserViewMapper(it)
        }
    }
}