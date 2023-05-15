package com.example.githublist.data

import com.example.githublist.data.model.RepoEntity
import com.example.githublist.data.model.UserDetailEntity
import com.example.githublist.data.model.UserEntity
import retrofit2.http.*

interface GithubAPI {

    @GET("users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") per_page: Int): List<UserEntity>

    @GET("users/{username}")
    suspend fun searchUser(@Path("username") username: String): UserDetailEntity

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): List<RepoEntity>

}
