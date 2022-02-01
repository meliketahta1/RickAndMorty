package com.example.rickandmorty.presentation.characterlist

import com.example.rickandmorty.data.model.ResultState
import com.example.rickandmorty.data.repository.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject


class FetchCharactersUseCase @Inject constructor(private val repository: RickAndMortyRepository){

    suspend fun invoke()= flow{
        emit(ResultState.Loading)
        try{
            val _characterList = repository.fetchRickAndMortyCharacters()
            _characterList?.let {
               emit(ResultState.Success(_characterList))

            }?:run{
                emit(ResultState.Error("EmptyList"))
            }

        }catch (exception:Exception){
            emit(ResultState.Error(exception.localizedMessage))
        }


    }.flowOn(Dispatchers.IO)
}