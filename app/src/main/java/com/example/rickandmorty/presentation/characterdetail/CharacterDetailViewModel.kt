package com.example.rickandmorty.presentation.characterdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Result
import com.example.rickandmorty.data.model.ResultState
import com.example.rickandmorty.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val fetchCharacterUseCase: FetchCharacterUseCase) :BaseViewModel(){

    var characterLoading = MutableLiveData<Boolean>()
        private set

    var characterError = MutableLiveData<Boolean>()
        private set

    var characterSuccess = MutableLiveData<Result>()
        private set

    fun fetchCharacter(id:Int) = viewModelScope.launch {
        fetchCharacterUseCase.invoke(id).collect { resultState->
            when(resultState){
                is ResultState.Loading->{
                    characterError.postValue(false)
                    characterLoading.postValue(true)
                }
                is ResultState.Error->{
                    characterError.postValue(true)
                    characterLoading.postValue(false)
                }
                is ResultState.Success->{
                    characterSuccess.postValue(resultState.data as Result)
                    characterError.postValue(false)
                    characterLoading.postValue(false)

                }
            }

        }
    }


}