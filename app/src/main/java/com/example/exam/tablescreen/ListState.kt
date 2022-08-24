package com.example.exam.tablescreen

sealed class ListState{
    object FirstGithubState: ListState()
    object FirstBitbucketState: ListState()
    object SortedState: ListState()
    object ReverseState: ListState()
    object DefaultState: ListState()
    data class SearchState(val query: String): ListState()
}
