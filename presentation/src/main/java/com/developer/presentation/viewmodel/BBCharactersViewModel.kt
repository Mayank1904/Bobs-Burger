package com.developer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.usecases.GetCharactersUseCase
import com.developer.presentation.extension.asResult
import com.developer.presentation.mappers.CharacterMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BBCharactersViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharactersUseCase,
    private val characterMapper: CharacterMapper
) : ViewModel() {
    private val _characterListFlow = MutableStateFlow<CharacterUIModel>(CharacterUIModel.Loading)
    val characterListFlow: StateFlow<CharacterUIModel> = _characterListFlow.asStateFlow()

    fun loadCharacters() =
        viewModelScope.launch {
            getCharacterUseCase(30).asResult().collect { result ->
                _characterListFlow.update {
                    when (result) {
                        is Result.Loading -> CharacterUIModel.Loading
                        is Result.Success -> CharacterUIModel.Success(
                            characterMapper.mapFromModel(
                                result.data
                            )
                        )

                        is Result.Error -> CharacterUIModel.Error(result.exception)
                    }
                }
            }
        }

}