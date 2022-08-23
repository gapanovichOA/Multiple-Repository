package com.example.exam.tablescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.GithubRepositoryModel
import com.example.exam.databinding.UserItemBinding

class RepositoryAdapter(
    private val repositories: List<GithubRepositoryModel>,
    private val onItemClicked: (GithubRepositoryModel) -> Unit
) : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun getItemCount() = repositories.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = UserItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RepositoryViewHolder(
            binding = binding,
            onItemClicked = onItemClicked
        )
    }
}
