package com.example.githublist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepoEntity(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String
) : Serializable