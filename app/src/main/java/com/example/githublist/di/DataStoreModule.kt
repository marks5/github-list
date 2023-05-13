package com.example.githublist.di

import android.content.Context
import com.example.githublist.data.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideUserDataStore(@ApplicationContext context: Context) =
        UserDataStore(context)
}