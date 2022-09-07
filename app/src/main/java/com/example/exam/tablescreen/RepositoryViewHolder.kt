package com.example.exam.tablescreen

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.RepositoryModel
import com.example.exam.R
import com.example.exam.databinding.UserItemBinding

class RepositoryViewHolder (
    private val binding: UserItemBinding,
    private val onItemClicked: (RepositoryModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repositoryModel: RepositoryModel) {
        with(binding) {
            when(repositoryModel){
                is RepositoryModel.GithubRepositoryModel ->{
                    Glide
                        .with(userImage.context)
                        .load(repositoryModel.owner.userImage)
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .fallback(R.drawable.ic_person)
                        .fitCenter()
                        .into(userImage)
                    title.text = repositoryModel.name
                    description.text = repositoryModel.description
                    repository.text = repositoryModel.type
                }
                is RepositoryModel.BitbucketRepositoryModel ->{
                    Glide
                        .with(userImage.context)
                        .load(repositoryModel.userImage)
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .fallback(R.drawable.ic_person)
                        .fitCenter()
                        .into(userImage)
                    title.text = repositoryModel.name
                    description.text = repositoryModel.description
                    repository.text = repositoryModel.type
                }
            }


            root.setOnClickListener {
                onItemClicked(repositoryModel)
            }
        }
    }
}
