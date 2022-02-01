package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.Result
import com.example.rickandmorty.data.model.RickAndMortyCharacterResponse
import com.example.rickandmorty.data.network.RickAndMortyService
import javax.inject.Inject

class RemoteDataSourceProvider @Inject constructor(private val service :RickAndMortyService) :RemoteDataSource {

    override suspend fun fetchRickAndMortyCharacters(): RickAndMortyCharacterResponse? {
        return service.fetchCharacters()
    }

    override suspend fun fetchCharacter(id: Int): Result? {
        return service.fetchCharacter(id)
    }

}