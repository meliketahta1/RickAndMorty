package com.example.rickandmorty.presentation.characterdetail

import com.example.rickandmorty.data.model.ResultState
import com.example.rickandmorty.data.repository.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class FetchCharacterUseCase @Inject constructor(private val repository: RickAndMortyRepository) {

    operator fun invoke(id:Int) = flow{
        emit(ResultState.Loading)
        try {
            val _char = repository.fetchCharacter(id)
            _char?.let {
                emit(ResultState.Success(_char))
            }?:run{
                emit(ResultState.Error("No Character Response"))
            }
        }catch(exception:Exception){
            emit(ResultState.Error(exception.localizedMessage))
        }

    }.flowOn(Dispatchers.IO)
}