package com.example.githublist.data

import com.example.githublist.model.Repository
import com.example.githublist.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GithubAPI {

    @GET("users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") per_page: Int): List<User>

    @GET("users/{username}")
    @Headers("Accept: application/json", "Authorization: Bearer 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")
    suspend fun searchUser(@Path("username") username: String): User

    @GET("users/{username}/repos")
    @Headers("Accept: application/json", "Authorization: Bearer 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")
    suspend fun getUserRepos(@Path("username") username: String): List<Repository>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubAPI {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAPI::class.java)
        }
    }
}
