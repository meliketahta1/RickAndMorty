package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.model.Result
import com.example.rickandmorty.data.model.RickAndMortyCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("/api/character")
    suspend fun fetchCharacters(
    ):RickAndMortyCharacterResponse?

    @GET("/api/character/{characterID}")
    suspend fun fetchCharacter(
        @Path("characterID")characterId:Int
    ):Result?
}