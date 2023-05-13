package com.example.githublist.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githublist.databinding.ActivityDetailBinding
import com.example.githublist.presentation.repo.RepositoriesActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var login: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getStringExtra(RepositoriesActivity.EXTRA_USER)?.let {
            login = it
            viewModel.setUsername(it)
        }

        viewModel.userStateLiveData.observe(this) {
            render(it)
        }

        binding.btnLoadRepo.setOnClickListener {
            val intent = Intent(this, RepositoriesActivity::class.java)
            intent.putExtra(RepositoriesActivity.EXTRA_USER, login)
            startActivity(intent)
        }
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

    private fun render(viewState: DetailViewState?) {
        return when (viewState) {
            DetailViewState.Loading -> showLoading()
            is DetailViewState.Results -> {
                hideLoading()
                displayUser(viewState.user)
            }
            is DetailViewState.Error -> {
                hideLoading()
                showErrorMessage(viewState.errorMessage)
            }
            else -> {}
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

    private fun displayUser(user: DetailUserView) {
        with(binding) {
            Glide.with(applicationContext).load(user.avatarUrl).into(view)
            content.text = "Login: ${user.login}\nBio: ${user.bio}\n" +
                    "Blog: ${user.blog}\nCompany: ${user.company}\nEmail: ${user.email}\n" +
                    "Location: ${user.location}\nName: ${user.name}"
        }
    }
}