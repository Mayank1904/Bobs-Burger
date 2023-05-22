package com.developer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.use_cases.GetCharacterByIdUseCase
import com.developer.presentation.mappers.CharacterItemMapper
import com.developer.presentation.utils.ExceptionHandler
import com.developer.presentation.utils.Result
import com.developer.presentation.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BBCharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val characterItemMapper: CharacterItemMapper,
) : ViewModel() {
    private val _characterDetailFlow =
        MutableStateFlow<CharacterDetailUIModel>(CharacterDetailUIModel.Loading)
    val characterDetailFlow: StateFlow<CharacterDetailUIModel> = _characterDetailFlow.asStateFlow()

    fun getCharacterDetail(characterId: Int) = viewModelScope.launch {
        getCharacterByIdUseCase(characterId).asResult().collect { result ->
            _characterDetailFlow.update {
                when (result) {
                    is Result.Error -> CharacterDetailUIModel.Error(ExceptionHandler.parse(result.exception))
                    is Result.Loading -> CharacterDetailUIModel.Loading
                    is Result.Success -> CharacterDetailUIModel.Success(
                        characterItemMapper.mapFromModel(
                            result.data
                        )
                    )
                }
            }
        }
    }
}