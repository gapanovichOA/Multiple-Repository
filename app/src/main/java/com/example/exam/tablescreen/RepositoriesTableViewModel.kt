package com.example.exam.tablescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.RepositoryModel
import com.example.domain.usecase.GetBitbucketRepositoriesUseCase
import com.example.domain.usecase.GetGithubRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepositoriesTableViewModel @Inject constructor(
    private val getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase,
    private val getBitbucketRepositoriesUseCase: GetBitbucketRepositoriesUseCase
): ViewModel() {

    suspend fun getGithubRepositoriesList():List<RepositoryModel.GithubRepositoryModel>{
        val list = viewModelScope.async {
            getGithubRepositoriesUseCase()
        }
        return list.await()
    }
    suspend fun getBitbucketRepositoriesList():List<RepositoryModel.BitbucketRepositoryModel>{
        val list = viewModelScope.async {
            getBitbucketRepositoriesUseCase()
        }
        return list.await()
    }

    suspend fun getRepositoriesList(): List<RepositoryModel>{
        return (getGithubRepositoriesList()+getBitbucketRepositoriesList())
    }

}