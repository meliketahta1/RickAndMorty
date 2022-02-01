package com.example.rickandmorty.data.model

sealed class ResultState<T>{

    data class Success<T>(val data : T) : ResultState<T>()
    data class Error<T>(val exception: T) : ResultState<T>()
    object Loading : ResultState<Nothing>()
}

