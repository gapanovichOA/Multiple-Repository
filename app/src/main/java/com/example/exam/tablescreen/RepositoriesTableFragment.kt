package com.example.exam.tablescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
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
        with(binding){
            viewLifecycleOwner.lifecycleScope.launch{
                repositoriesList.adapter = RepositoryAdapter(viewModel.getRepositoriesList()){ repository->
                    when(repository){
                        is RepositoryModel.GithubRepositoryModel->{
                            findNavController().navigate(RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                                repository.name, repository.owner.userImage, repository.description, "Github"
                            ))
                        }
                        is RepositoryModel.BitbucketRepositoryModel ->{
                            findNavController().navigate(RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                                repository.name, repository.userImage, repository.description, "Bitbucket"
                            ))
                        }
                    }

                }
            }
            repositoriesList.layoutManager = LinearLayoutManager(view.context)
            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.search -> {
                        true
                    }
                    R.id.more -> {
                        true
                    }
                    R.id.option_1->{
                        true
                    }
                    R.id.option_2->{
                        true
                    }R.id.option_3->{
                        true
                    }R.id.option_4->{
                        true
                    }R.id.option_5->{
                        true
                    }
                    else -> false
                }
            }
        }
    }

}