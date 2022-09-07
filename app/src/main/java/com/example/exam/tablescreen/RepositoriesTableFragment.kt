package com.example.exam.tablescreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.RepositoryModel
import com.example.exam.BaseFragment
import com.example.exam.R
import com.example.exam.databinding.FragmentRepositoriesTableBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RepositoriesTableFragment : BaseFragment<FragmentRepositoriesTableBinding>() {

    private val viewModel: RepositoriesTableViewModel by viewModels()
    override fun getViewBinding() = FragmentRepositoriesTableBinding.inflate(layoutInflater)
    var debouncePeriod: Long = 500
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onShowScreen()
            }
            viewModel.repositoriesList.observe(viewLifecycleOwner) {
                repositoriesList.adapter =
                    RepositoryAdapter(viewModel.repositoriesList.value!!) { repository ->
                        when (repository) {
                            is RepositoryModel.GithubRepositoryModel -> {
                                findNavController().navigate(
                                    RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                                        repository.name,
                                        repository.owner.userImage,
                                        repository.description,
                                        repository.type
                                    )
                                )
                            }
                            is RepositoryModel.BitbucketRepositoryModel -> {
                                findNavController().navigate(
                                    RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                                        repository.name,
                                        repository.userImage,
                                        repository.description,
                                        repository.type
                                    )
                                )
                            }
                        }
                    }
            }
            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), viewModel.errorLiveData.value, Toast.LENGTH_LONG)
                    .show()
            }
            repositoriesList.layoutManager = LinearLayoutManager(view.context)
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.onRefresh()
                swipeRefreshLayout.isRefreshing = false
            }
            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.search -> {
                        val searchView = menuItem.actionView as SearchView
                        searchView.setOnQueryTextListener(object :
                            SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                viewModel.onQuerySubmit(query ?: "")
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                searchJob?.cancel()
                                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                                    newText?.let {
                                        delay(debouncePeriod)
                                        viewModel.onQuerySubmit(newText)
                                    }
                                }
                                return false
                            }
                        })
                        viewModel.queryLiveData.observe(viewLifecycleOwner) { query ->
                            viewModel.onClickSearch(query)
                        }
                        true
                    }
                    R.id.more -> {
                        true
                    }
                    R.id.first_github -> {
                        viewModel.onChangedState(ListState.FirstGithubState)
                        true
                    }
                    R.id.first_bitbucket -> {
                        viewModel.onChangedState(ListState.FirstBitbucketState)
                        true
                    }
                    R.id.alphabetically -> {
                        viewModel.onChangedState(ListState.SortedState)
                        true
                    }
                    R.id.reverse -> {
                        viewModel.onChangedState(ListState.ReverseState)
                        true
                    }
                    R.id.default_order -> {
                        viewModel.onChangedState(ListState.DefaultState)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}

