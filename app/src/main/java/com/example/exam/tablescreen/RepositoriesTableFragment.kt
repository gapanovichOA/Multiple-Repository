package com.example.exam.tablescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.exam.databinding.FragmentRepositoriesTableBinding

class RepositoriesTableFragment : Fragment() {

    private val viewModel: RepositoriesTableViewModel by viewModels()
    private val binding = FragmentRepositoriesTableBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}