package com.example.githublist.helper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.example.githublist.presentation.list.UserAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatchers {

    companion object {
        fun withItemAtPositionAndLogin(position: Int, login: String): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun matchesSafely(item: RecyclerView?): Boolean {
                    val userAdapter = item?.adapter as UserAdapter
                    return userAdapter.currentList[position].login == login
                }

                override fun describeTo(description: Description?) {
                    description?.appendText("User at position $position with login $login")
                }
            }
        }
    }
}