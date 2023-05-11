package com.example.githublist.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githublist.Injection
import com.example.githublist.R
import com.example.githublist.databinding.ActivityListUsersBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListUsersActivity : AppCompatActivity() {

    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListUsersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        val viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
            .get(GitHubViewModel::class.java)

        lifecycleScope.launch {
            viewModel.users.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.list.visibility =
                    if (loadStates.refresh is LoadState.NotLoading && adapter.itemCount > 0) View.VISIBLE else View.GONE

                if (loadStates.refresh is LoadState.Error) {
                    val message = (loadStates.refresh as LoadState.Error).error.message
                    if (message != null) {
//                        showSnackbar(message)
                    }
                }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        val searchItem = menu?.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
//        searchView.queryHint = "Search users"
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (!query.isNullOrEmpty()) {
//                    viewModel.searchUsers(query)
//                }
//                searchItem?.collapseActionView()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (!newText.isNullOrEmpty()) {
//                    viewModel.searchUsers(newText)
//                }
//                return true
//            }
//        })
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_refresh -> {
//                adapter.refresh()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    private fun showSnackbar(message: String) {
//        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show()
//    }
}