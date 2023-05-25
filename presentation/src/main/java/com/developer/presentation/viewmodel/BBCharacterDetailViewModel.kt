package com.developer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.usecases.GetCharacterByIdUseCase
import com.developer.presentation.extension.asResult
import com.developer.presentation.mappers.CharacterItemMapper
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
class BBCharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val characterItemMapper: CharacterItemMapper,
) : ViewModel() {
    private val _characterDetailFlow =
        MutableStateFlow<CharacterDetailUIModel>(CharacterDetailUIModel.Loading)
    val characterDetailFlow: StateFlow<CharacterDetailUIModel> = _characterDetailFlow.asStateFlow()

    fun getCharacterDetail(characterId: Int) = viewModelScope.launch(Dispatchers.Main) {
        getCharacterByIdUseCase(characterId).flowOn(Dispatchers.IO).asResult().collect { result ->
            _characterDetailFlow.update {
                when (result) {
                    is Result.Error -> CharacterDetailUIModel.Error(result.exception?.localizedMessage)
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