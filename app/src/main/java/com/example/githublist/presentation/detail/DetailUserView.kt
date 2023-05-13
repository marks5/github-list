package com.example.githublist.presentation.detail

data class DetailUserView(
    val login: String,
    val avatarUrl: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
    val name: String
)