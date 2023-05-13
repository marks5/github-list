package com.example.githublist.di

import android.content.Context
import androidx.room.Room
import com.example.githublist.data.GithubAPI
import com.example.githublist.data.UserDatabase
import com.example.githublist.presentation.custom.StringResources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com/"

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val okHttpClient = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserNetwork(
        retrofit: Retrofit
    ): GithubAPI = retrofit.create(GithubAPI::class.java)

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "users_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase) = userDatabase.userDao

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO

    @Provides
    fun provideStringResources(@ApplicationContext context: Context) = StringResources(context)
}