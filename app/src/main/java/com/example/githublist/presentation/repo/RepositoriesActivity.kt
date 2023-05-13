package com.example.githublist.presentation.repo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githublist.databinding.ActivityRepoUserBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private var _binding: ActivityRepoUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var repoAdapter: RepoAdapter

    private val viewModel: RepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRepoUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getStringExtra(EXTRA_USER)?.let {
            viewModel.setUsername(it)
        }

        viewModel.repoStateLiveData.observe(this) {
            render(it)
        }

        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        repoAdapter = RepoAdapter()

        linearLayoutManager = LinearLayoutManager(binding.root.context)
        binding.list.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(binding.root.context,
                (layoutManager as LinearLayoutManager).orientation)
            )
            adapter = repoAdapter
        }
    }

    private fun render(viewState: RepositoriesViewState?) {
        return when (viewState) {
            RepositoriesViewState.Loading -> showLoading()
            is RepositoriesViewState.Results -> {
                hideLoading()
                displayRepositories(viewState.repos)
            }
            is RepositoriesViewState.Error -> {
                hideLoading()
                showErrorMessage(viewState.errorMessage)
            }
            else -> {}
        }
    }

    private fun displayRepositories(repos: List<RepoView>) {
        repoAdapter.submitList(repos)
    }

    private fun showErrorMessage(errorMessage: String?) {
        errorMessage?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}