package com.example.githublist.presentation.detail

sealed class DetailViewState {
    object Loading : DetailViewState()
    class Results(val user: DetailUserView) : DetailViewState()
    class Error(val errorMessage: String?) : DetailViewState()
}