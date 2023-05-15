package com.example.githublist.mock

import android.content.Context
import com.example.githublist.data.UserDataStore
import com.example.githublist.di.DataStoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)
class MockModule {

    @Provides
    @Singleton
    fun provideUrl(): String = "http://localhost:1234/"

    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context) : UserDataStore =
        MockUserDataStore(context)
}