package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.Result
import com.example.rickandmorty.data.model.RickAndMortyCharacterResponse

interface RemoteDataSource {

    suspend fun fetchRickAndMortyCharacters() :RickAndMortyCharacterResponse?

    suspend fun fetchCharacter(id:Int) :Result?
}