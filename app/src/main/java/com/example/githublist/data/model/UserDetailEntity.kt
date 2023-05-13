package com.example.githublist.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDetailEntity(
    @SerializedName("login"               ) var login             : String?  = null,
    @SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
    @SerializedName("name"                ) var name              : String?  = null,
    @SerializedName("company"             ) var company           : String?  = null,
    @SerializedName("blog"                ) var blog              : String?  = null,
    @SerializedName("location"            ) var location          : String?  = null,
    @SerializedName("email"               ) var email             : String?  = null,
    @SerializedName("hireable"            ) var hireable          : String?  = null,
    @SerializedName("bio"                 ) var bio               : String?  = null,
    @SerializedName("followers"           ) var followers         : Int?     = null,
    @SerializedName("following"           ) var following         : Int?     = null,
) : Serializable