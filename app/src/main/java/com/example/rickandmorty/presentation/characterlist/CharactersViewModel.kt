package com.example.rickandmorty.presentation.characterlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.ResultState.*
import com.example.rickandmorty.data.model.RickAndMortyCharacterResponse
import com.example.rickandmorty.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val fetchCharactersUseCase: FetchCharactersUseCase):BaseViewModel() {

    var charactersSuccesState = MutableLiveData<RickAndMortyCharacterResponse>()
        private set

    var characterLoadingState = MutableLiveData<Boolean>()
        private set

    var characterErrorState = MutableLiveData<Boolean>()
        private set

    fun fetchCharacters() = viewModelScope.launch {
        fetchCharactersUseCase.invoke().collect {resultState->
            when(resultState){
                is Loading->{
                    characterLoadingState.postValue(true)
                    characterErrorState.postValue(false)
                }
                is Success ->{
                    charactersSuccesState.postValue(resultState.data as RickAndMortyCharacterResponse?)
                    characterLoadingState.postValue(false)
                    characterErrorState.postValue(false)
                }
                is Error->{
                    characterLoadingState.postValue(false)
                    characterErrorState.postValue(true)
                }
            }
        }

    }


}