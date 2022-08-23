package com.example.exam.detailsscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.exam.databinding.FragmentRepositoryDetailsBinding

class RepositoryDetailsFragment : Fragment() {

    private val viewModel: RepositoryDetailsViewModel by viewModels()
    private val binding = FragmentRepositoryDetailsBinding.inflate(layoutInflater)

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