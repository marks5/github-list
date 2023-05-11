package com.example.githublist.presentation.repo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.githublist.Injection
import com.example.githublist.databinding.ActivityRepoUserBinding
import com.example.githublist.presentation.list.GitHubViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RepositoriesActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityRepoUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
            .get(RepoViewModel::class.java)

        intent.getStringExtra(EXTRA_USER)?.let {
            viewModel.setUsername(it)
        }

        val adapter = ReposAdapter()
        binding.list.adapter = adapter

        viewModel.getRepos().observe(this@RepositoriesActivity) { repositories ->
            adapter.submitData(lifecycle, repositories)
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val isListEmpty = loadStates.refresh is LoadState.NotLoading && adapter.itemCount == 0
                binding.progressBar.isVisible = loadStates.source.refresh is LoadState.Loading
                binding.list.isVisible = !isListEmpty
                binding.emptyList.isVisible = isListEmpty
                binding.retryButton.isVisible = loadStates.source.refresh is LoadState.Error
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
}