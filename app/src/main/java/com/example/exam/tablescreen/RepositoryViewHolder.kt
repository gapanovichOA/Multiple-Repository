package com.example.exam.tablescreen

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.domain.model.RepositoryModel
import com.example.exam.databinding.UserItemBinding

class RepositoryViewHolder (
    private val binding: UserItemBinding,
    private val onItemClicked: (RepositoryModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repositoryModel: RepositoryModel) {
        with(binding) {
            when(repositoryModel){
                is RepositoryModel.GithubRepositoryModel ->{
                    userImage.load(repositoryModel.owner.userImage){
                        scale(Scale.FIT)
                        size(ViewSizeResolver(root))
                    }
                    title.text = repositoryModel.name
                    description.text = repositoryModel.description
                    repository.text = "Github"
                }
                is RepositoryModel.BitbucketRepositoryModel ->{
                    userImage.load(repositoryModel.userImage){
                        scale(Scale.FIT)
                        size(ViewSizeResolver(root))
                    }
                    title.text = repositoryModel.name
                    description.text = repositoryModel.description
                    repository.text = "Bitbucket"
                }
            }


            root.setOnClickListener {
                onItemClicked(repositoryModel)
            }
        }
    }
}
