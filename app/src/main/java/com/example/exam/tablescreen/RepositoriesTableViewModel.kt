package com.example.exam.tablescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.GithubRepositoryModel
import com.example.domain.usecase.GetGithubRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class RepositoriesTableViewModel @Inject constructor(
    private val getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase
): ViewModel() {

    suspend fun getRepositoriesList():List<GithubRepositoryModel>{
        val list = viewModelScope.async {
            getGithubRepositoriesUseCase()
        }
        return list.await()
    }

}