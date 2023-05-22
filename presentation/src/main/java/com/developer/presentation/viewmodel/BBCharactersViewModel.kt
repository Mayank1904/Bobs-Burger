package com.developer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.use_cases.GetCharactersUseCase
import com.developer.presentation.utils.ExceptionHandler
import com.developer.presentation.utils.Result
import com.developer.presentation.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class BBCharactersViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharactersUseCase) : ViewModel() {

    private val _characterListFlow = MutableStateFlow<CharacterUIModel>(CharacterUIModel.Loading)
    val characterListFlow: StateFlow<CharacterUIModel> = _characterListFlow.asStateFlow()

     fun loadCharacters() =
         viewModelScope.launch {
                 getCharacterUseCase(Unit).flowOn(Dispatchers.IO).asResult().collect { result ->
                     _characterListFlow.update {
                         when(result){
                             is Result.Loading -> CharacterUIModel.Loading
                             is Result.Success -> CharacterUIModel.Success(result.data)
                             is Result.Error -> CharacterUIModel.Error(ExceptionHandler.parse(result.exception))
                         }
                     }
                 }
         }

}