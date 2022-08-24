package com.example.exam.tablescreen

sealed class ListState{
    object FirstGithubState: ListState()
    object FirstBitbucketState: ListState()
    object SortedState: ListState()
    object ReverseState: ListState()
    object DefaultState: ListState()
}
