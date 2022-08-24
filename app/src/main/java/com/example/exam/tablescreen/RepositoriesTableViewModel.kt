package com.example.exam.tablescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.toGeneralModel
import com.example.domain.model.GeneralRepositoryModel
import com.example.domain.model.GithubUserModel
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
) : ViewModel() {

    val repositoryState = MutableLiveData<ListState>()
    private val githubList = mutableListOf<RepositoryModel.GithubRepositoryModel>()
    private val bitbucketList = mutableListOf<RepositoryModel.BitbucketRepositoryModel>()

    fun onChangedState(state: ListState) {
        repositoryState.value = state
    }

    private suspend fun getGithubRepositoriesList(): List<RepositoryModel.GithubRepositoryModel> {
        val list = viewModelScope.async {
            getGithubRepositoriesUseCase()
        }
        githubList.addAll(list.await())
        return githubList
    }

    private suspend fun getBitbucketRepositoriesList(): List<RepositoryModel.BitbucketRepositoryModel> {
        val list = viewModelScope.async {
            getBitbucketRepositoriesUseCase()
        }
        bitbucketList.addAll(list.await())
        return bitbucketList
    }

    suspend fun getRepositoriesList(): List<RepositoryModel> {
        if (bitbucketList.isEmpty() && githubList.isEmpty()) {
            return viewModelScope.async {
                getBitbucketRepositoriesList() + getGithubRepositoriesList()
            }.await()
        }
        return when (repositoryState.value) {
            is ListState.FirstGithubState -> {
                githubList + bitbucketList
            }
            is ListState.FirstBitbucketState -> {
                bitbucketList + githubList
            }
            is ListState.SortedState -> {
                val list = githubList + bitbucketList
                list.sortAlphabetical()
            }
            is ListState.ReverseState -> {
                val list = githubList + bitbucketList
                list.sortAlphabetical().reversed()
            }
            is ListState.DefaultState -> {
                bitbucketList + githubList
            }
            else -> {
                emptyList()
            }
        }
    }

    init {
        repositoryState.value = ListState.FirstBitbucketState
    }

}

private fun List<RepositoryModel>.sortAlphabetical(): List<RepositoryModel>{
    val sortedList = this.map {
        when (it) {
            is RepositoryModel.GithubRepositoryModel -> {
                it.toGeneralModel()
            }
            is RepositoryModel.BitbucketRepositoryModel -> {
                it.toGeneralModel()
            }
        }
    }.sortedWith(compareBy { it.name })
    return sortedList.map {
        if ((it.type.equals("Github"))) {
            RepositoryModel.GithubRepositoryModel(
                it.name,
                GithubUserModel(it.userImage), it.description
            )
        } else {
            RepositoryModel.BitbucketRepositoryModel(
                it.name, it.description, it.userImage
            )
        }
    }
}