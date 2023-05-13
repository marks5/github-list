package com.example.githublist.presentation.list

import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githublist.data.model.UserFetchError
import com.example.githublist.domain.useCase.FetchNewUsersUseCase
import com.example.githublist.domain.useCase.FilterLocalUsersUseCase
import com.example.githublist.domain.useCase.QueryLocalUsersUseCase
import com.example.githublist.presentation.custom.SingleLiveData
import com.example.githublist.presentation.custom.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val fetchNewUsersUseCase: FetchNewUsersUseCase,
    private val queryLocalUsersUseCase: QueryLocalUsersUseCase,
    private val filterLocalUsersUseCase: FilterLocalUsersUseCase,
    private val userDomainToUserViewMapper: UserDomainToUserViewMapper,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val stringResources: StringResources
) : ViewModel() {

    private val userListViewState: MutableLiveData<UserListViewState> = MutableLiveData()
    val userListViewStateLiveData: LiveData<UserListViewState> = userListViewState

    private val userListAction: SingleLiveData<UserListAction> = SingleLiveData()
    val userListActionLiveData: LiveData<UserListAction> = userListAction

    private var isRequestingUsers = false
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var filterApplied = ""

    init {
        loadUsers()
    }

    fun onScroll(searchText: String, totalItemCount: Int, lastVisibleItemPosition: Int) {
        if (!isRequestingUsers
            && searchText.isEmpty()
            && totalItemCount == lastVisibleItemPosition + 1) {
            loadUsers()
        }
    }

    fun loadUsers() {
        if (filterApplied.isNotEmpty()) return
        userListViewState.value = UserListViewState.Loading

        viewModelScope.launch {
            try {
                withContext(coroutineDispatcher) {
                    isRequestingUsers = true
                    fetchNewUsersUseCase.execute()
                }
                val userList = queryLocalUsers()
                userListViewState.value = UserListViewState.Results(userList)
            } catch (error: UserFetchError) {
                userListViewState.value =
                    UserListViewState.Error(stringResources.getUserFetchErrorMessage())
            } finally {
                isRequestingUsers = false
            }
        }
    }

    private suspend fun queryLocalUsers() =
        withContext(coroutineDispatcher) {
            queryLocalUsersUseCase.execute()?.map {
                userDomainToUserViewMapper.userDomainToUserViewMapper(it)
            } ?: emptyList()
        }

    fun onUserClicked(user: UserView) {
        userListAction.value = UserListAction.Navigate(user.login)
    }

    fun filterUsers(filter: String) {
        filterApplied = filter

        viewModelScope.launch {
            val filteredUserList = filterLocalUsers(filter)
            userListViewState.value = UserListViewState.Results(filteredUserList)
        }
    }

    private suspend fun filterLocalUsers(filter: String) =
        withContext(coroutineDispatcher) {
            filterLocalUsersUseCase.execute(filter)?.map {
                userDomainToUserViewMapper.userDomainToUserViewMapper(it)
            } ?: emptyList()
        }
}