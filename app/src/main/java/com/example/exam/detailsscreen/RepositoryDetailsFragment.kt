package com.example.exam.detailsscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.exam.BaseFragment
import com.example.exam.databinding.FragmentRepositoryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailsFragment : BaseFragment<FragmentRepositoryDetailsBinding>() {

    private val navArguments: RepositoryDetailsFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentRepositoryDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            title.text = navArguments.title
            description.text = navArguments.description
            repository.text = navArguments.repository
            userImage.load(navArguments.userImage){
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
        }
    }

}