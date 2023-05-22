package com.developer.presentation.viewmodel

import com.developer.domain.models.CharacterEntityItem

sealed class CharacterDetailUIModel {
    object Loading : CharacterDetailUIModel()
    data class Error(var error: Int) : CharacterDetailUIModel()
    data class Success(val data: CharacterEntityItem) : CharacterDetailUIModel()
}