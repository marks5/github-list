package com.example.githublist.di

import com.example.githublist.data.RepositoriesRepositoryImpl
import com.example.githublist.data.UserRepositoryImpl
import com.example.githublist.domain.RepositoriesRepository
import com.example.githublist.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindRepositories(repositoryImpl: RepositoriesRepositoryImpl): RepositoriesRepository
}