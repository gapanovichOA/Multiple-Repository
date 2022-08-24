package com.example.exam.tablescreen

import androidx.lifecycle.MutableLiveData
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

    val repositoryState = MutableLiveData<ListState>()

    fun onChangedState(state: ListState){
        repositoryState.value = state
    }

    private suspend fun getGithubRepositoriesList():List<RepositoryModel.GithubRepositoryModel>{
        val list = viewModelScope.async {
            getGithubRepositoriesUseCase()
        }
        return list.await()
    }
    private suspend fun getBitbucketRepositoriesList():List<RepositoryModel.BitbucketRepositoryModel>{
        val list = viewModelScope.async {
            getBitbucketRepositoriesUseCase()
        }
        return list.await()
    }

    suspend fun getRepositoriesList(): List<RepositoryModel>{
        return when(repositoryState.value){
            is ListState.FirstGithubState->{
                getGithubRepositoriesList()+getBitbucketRepositoriesList()
            }
            is ListState.FirstBitbucketState->{
                (getBitbucketRepositoriesList()+getGithubRepositoriesList())
            }
            is ListState.SortedState ->{
                val set = mutableSetOf<RepositoryModel>()
                set.addAll(getGithubRepositoriesList()+getBitbucketRepositoriesList())
                set.toList()
            }
            is ListState.ReverseState ->{
                val set = mutableSetOf<RepositoryModel>()
                set.addAll(getGithubRepositoriesList()+getBitbucketRepositoriesList())
                set.reversed().toList()
            }
            is ListState.DefaultState->{
                getBitbucketRepositoriesList()+getGithubRepositoriesList()
            }
            else -> {
                emptyList()
            }
        }
    }

    init{
        repositoryState.value = ListState.FirstBitbucketState
    }

}