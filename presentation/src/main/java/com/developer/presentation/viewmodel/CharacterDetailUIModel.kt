package com.developer.presentation.viewmodel

import com.developer.presentation.models.CharacterModel

sealed class CharacterDetailUIModel {
    object Loading : CharacterDetailUIModel()
    data class Error(var error: String) : CharacterDetailUIModel()
    data class Success(val data: CharacterModel) : CharacterDetailUIModel()
}