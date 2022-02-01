package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.remote.RemoteDataSourceProvider
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(private val remoteDataSourceProvider: RemoteDataSourceProvider) {

    suspend fun fetchRickAndMortyCharacters()=
        remoteDataSourceProvider.fetchRickAndMortyCharacters()

    suspend fun fetchCharacter(id:Int) = remoteDataSourceProvider.fetchCharacter(id)
}