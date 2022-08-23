package com.example.exam.tablescreen

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.domain.model.GithubRepositoryModel
import com.example.exam.databinding.UserItemBinding

class RepositoryViewHolder (
    private val binding: UserItemBinding,
    private val onItemClicked: (GithubRepositoryModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repositoryModel: GithubRepositoryModel) {
        with(binding) {
            userImage.load(repositoryModel.owner.userImage){
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            title.text = repositoryModel.name
            description.text = repositoryModel.description
            repository.text = "Github"

            root.setOnClickListener {
                onItemClicked(repositoryModel)
            }
        }
    }
}
