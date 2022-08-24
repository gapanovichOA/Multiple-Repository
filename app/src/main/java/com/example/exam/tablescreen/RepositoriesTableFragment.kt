package com.example.exam.tablescreen

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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoriesTableFragment : BaseFragment<FragmentRepositoriesTableBinding>() {

    private val viewModel: RepositoriesTableViewModel by viewModels()
    override fun getViewBinding() = FragmentRepositoriesTableBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.repositoryState.observe(viewLifecycleOwner) {
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        repositoriesList.adapter =
                            RepositoryAdapter(viewModel.getRepositoriesList()) { repository ->
                                when (repository) {
                                    is RepositoryModel.GithubRepositoryModel -> {
                                        findNavController().navigate(
                                            RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                                                repository.name,
                                                repository.owner.userImage,
                                                repository.description,
                                                "Github"
                                            )
                                        )
                                    }
                                    is RepositoryModel.BitbucketRepositoryModel -> {
                                        findNavController().navigate(
                                            RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                                                repository.name,
                                                repository.userImage,
                                                repository.description,
                                                "Bitbucket"
                                            )
                                        )
                                    }
                                }

                            }
                    }catch(exception: Exception){
                        Toast.makeText(requireContext(),exception.message,Toast.LENGTH_LONG).show()
                    }
                }
                repositoriesList.layoutManager = LinearLayoutManager(view.context)
                topAppBar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.search -> {
                            val searchView = menuItem.actionView as SearchView
                            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(query: String?): Boolean {
                                    viewModel.onQuerySubmit(query ?: "")
                                    return false
                                }
                                override fun onQueryTextChange(newText: String?): Boolean {
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
                        R.id.option_1 -> {
                            viewModel.onChangedState(ListState.FirstGithubState)
                            true
                        }
                        R.id.option_2 -> {
                            viewModel.onChangedState(ListState.FirstBitbucketState)
                            true
                        }
                        R.id.option_3 -> {
                            viewModel.onChangedState(ListState.SortedState)
                            true
                        }
                        R.id.option_4 -> {
                            viewModel.onChangedState(ListState.ReverseState)
                            true
                        }
                        R.id.option_5 -> {
                            viewModel.onChangedState(ListState.DefaultState)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }
}