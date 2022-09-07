package com.example.exam.tablescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.toGeneralModel
import com.example.domain.model.GithubUserModel
import com.example.domain.model.RepositoryModel
import com.example.domain.usecase.CheckInternetConnectionUseCase
import com.example.domain.usecase.GetBitbucketRepositoriesUseCase
import com.example.domain.usecase.GetGithubRepositoriesUseCase
import com.example.exam.App
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepositoriesTableViewModel @Inject constructor(
    private val getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase,
    private val getBitbucketRepositoriesUseCase: GetBitbucketRepositoriesUseCase,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase
) : ViewModel() {

    private var _repositoryState = MutableLiveData<ListState>()
    val repositoryState: LiveData<ListState>
        get() = _repositoryState
    private val githubList = mutableListOf<RepositoryModel.GithubRepositoryModel>()
    private val bitbucketList = mutableListOf<RepositoryModel.BitbucketRepositoryModel>()
    private var _repositoriesList = MutableLiveData<List<RepositoryModel>>()
    val repositoriesList: LiveData<List<RepositoryModel>>
        get() = _repositoriesList
    private var _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    private var _queryLiveData: MutableLiveData<String> = MutableLiveData()
    val queryLiveData: LiveData<String>
        get() = _queryLiveData

    fun onQuerySubmit(query: String) {
        _queryLiveData.value = query
    }

    fun onChangedState(state: ListState = ListState.DefaultState) {
        _repositoryState.value = state
        when (repositoryState.value) {
            is ListState.FirstGithubState -> {
                _repositoriesList.value = githubList + bitbucketList
            }
            is ListState.FirstBitbucketState -> {
                _repositoriesList.value = bitbucketList + githubList
            }
            is ListState.SortedState -> {
                val list = githubList + bitbucketList
                _repositoriesList.value = list.sortAlphabetical()
            }
            is ListState.ReverseState -> {
                val list = githubList + bitbucketList
                _repositoriesList.value = list.sortAlphabetical().reversed()
            }
            is ListState.DefaultState -> {
                _repositoriesList.value = bitbucketList + githubList
            }
            is ListState.SearchState -> {
                _repositoriesList.value =
                    (bitbucketList + githubList).find(queryLiveData.value ?: "")
            }
            else -> {
                _repositoriesList.value = emptyList()
            }
        }
    }

    fun onClickSearch(query: String) {
        _repositoryState.value = ListState.SearchState(query)
    }

    fun onRefresh() {
        bitbucketList.clear()
        githubList.clear()
        onChangedState()
    }

    suspend fun onShowScreen() {
        if (checkInternetConnectionUseCase.invoke()) {
            viewModelScope.launch {
                try {
                    val githubRep = async { getGithubRepositoriesUseCase() }
                    val bitbucketRep = async { getBitbucketRepositoriesUseCase() }
                    githubList.addAll(githubRep.await())
                    bitbucketList.addAll(bitbucketRep.await())
                    _repositoriesList.value = bitbucketList + githubList
                } catch (exception: Exception) {
                    _errorLiveData.value = exception.message
                    _repositoriesList.value = emptyList()
                }
            }
        } else {
            _errorLiveData.value = "No Internet"
        }
    }

    init {
        _repositoryState.value = ListState.FirstBitbucketState
    }

}

private fun List<RepositoryModel>.sortAlphabetical(): List<RepositoryModel> {
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
        if ((it.type == "Github")) {
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

private fun List<RepositoryModel>.find(query: String): List<RepositoryModel> {
    val list = this.map {
        when (it) {
            is RepositoryModel.GithubRepositoryModel -> {
                it.toGeneralModel()
            }
            is RepositoryModel.BitbucketRepositoryModel -> {
                it.toGeneralModel()
            }
        }
    }.filter {
        it.name.contains(query) || it.type.contains(query)
    }
    return list.map {
        if ((it.type == "Github")) {
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

