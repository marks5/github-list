package com.example.githublist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserEntity(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Serializable