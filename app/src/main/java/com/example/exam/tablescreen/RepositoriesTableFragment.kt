package com.example.exam.tablescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam.BaseFragment
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
                repositoriesList.adapter = RepositoryAdapter(viewModel.getRepositoriesList()){
                    findNavController().navigate(RepositoriesTableFragmentDirections.actionRepositoriesTableFragmentToRepositoryDetailsFragment(
                        it.name, it.owner.userImage, it.description
                    ))
                }
            }
            repositoriesList.layoutManager = LinearLayoutManager(view.context)
        }
    }

}