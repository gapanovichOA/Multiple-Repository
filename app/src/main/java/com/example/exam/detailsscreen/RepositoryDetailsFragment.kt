package com.example.exam.detailsscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.exam.BaseFragment
import com.example.exam.R
import com.example.exam.databinding.FragmentRepositoryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailsFragment : BaseFragment<FragmentRepositoryDetailsBinding>() {

    private val viewModel: RepositoryDetailsViewModel by viewModels()
    private val navArguments: RepositoryDetailsFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentRepositoryDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            title.text = navArguments.title
            description.text = navArguments.description
            repository.text = navArguments.repository
            context?.let {
                Glide
                    .with(it)
                    .load(navArguments.userImage)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .fallback(R.drawable.ic_person)
                    .fitCenter()
                    .into(userImage)
            }
        }
    }

}