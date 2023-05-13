package com.example.githublist.presentation.custom

import android.content.Context
import com.example.githublist.R
import javax.inject.Inject

class StringResources @Inject constructor(
    private val context: Context
) {
    fun getUserFetchErrorMessage() = context.getString(R.string.user_fetch_error)
}