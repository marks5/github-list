package com.example.githublist.presentation.detail

import androidx.lifecycle.*
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.useCase.FetchUserUseCase
import com.example.githublist.presentation.custom.SingleLiveData
import com.example.githublist.presentation.custom.StringResources
import com.example.githublist.presentation.list.UserListAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchUser: FetchUserUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val stringResources: StringResources,
    private val userDomainToViewMapper: UserDetailDomainToUserDetailViewMapper,
    ) : ViewModel() {

    private val repoState: MutableLiveData<DetailViewState> = MutableLiveData()
    val userStateLiveData: LiveData<DetailViewState> = repoState

    fun setUsername(login: String) {
        this.repoState.value = DetailViewState.Loading

        viewModelScope.launch {
            try {
                withContext(coroutineDispatcher) {
                    repoState.postValue(DetailViewState.Results(getUser(login)))
                }
            } catch (error: UserFetchError) {
                repoState.value = DetailViewState.Error(stringResources.getUserFetchErrorMessage())
            }
        }
    }

    private suspend fun getUser(login: String) = withContext(coroutineDispatcher) {
        userDomainToViewMapper.userDomainToUserViewMapper(fetchUser.execute(login))
    }
}