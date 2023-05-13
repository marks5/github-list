package com.example.githublist.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githublist.databinding.ActivityListUsersBinding
import com.example.githublist.presentation.detail.DetailActivity
import com.example.githublist.presentation.repo.RepositoriesActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class ListUsersActivity : AppCompatActivity() {

    private var _binding: ActivityListUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var userAdapter: UserAdapter

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListUsersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.userListViewStateLiveData.observe(this) { render(it) }
        viewModel.userListActionLiveData.observe(this) { perform(it) }

        bindingProperties()
        initViews()
    }

    private fun initViews() = with(binding) {
        initRecyclerView()

        retryButton.setOnClickListener {
            viewModel.loadUsers()
        }
    }

    private fun render(viewState: UserListViewState?) {
        return when (viewState) {
            UserListViewState.Loading -> showLoading()
            is UserListViewState.Results -> {
                hideLoading()
                hideRetry()
                displayUsers(viewState.users)
            }
            is UserListViewState.Error -> {
                hideLoading()
                showRetry()
                showErrorMessage(viewState.errorMessage)
            }
            else -> {}
        }
    }

    private fun showRetry() {
        binding.retryButton.visibility = View.VISIBLE
    }

    private fun hideRetry() {
        binding.retryButton.visibility = View.GONE
    }

    private fun perform(action: UserListAction) {
        return when (action) {
            is UserListAction.Navigate -> navigate(action.login)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorMessage(errorMessage: String?) {
        errorMessage?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
    }

    private fun displayUsers(userList: List<UserView>) {
        userAdapter.submitList(userList)
    }

    private fun navigate(login: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(RepositoriesActivity.EXTRA_USER, login)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(user: UserView) {
                    viewModel.onUserClicked(user)
                }
            }
        )

        linearLayoutManager = LinearLayoutManager(binding.root.context)
        binding.list.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(binding.root.context,
                (layoutManager as LinearLayoutManager).orientation))
            adapter = userAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindingProperties() = with(binding) {
        search.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.filterUsers(it) }
                return true
            }
        })
    }

}