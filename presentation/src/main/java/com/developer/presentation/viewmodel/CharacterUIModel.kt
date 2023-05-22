package com.developer.presentation.viewmodel

import com.developer.presentation.models.CharacterModel

sealed class CharacterUIModel {
    object Loading : CharacterUIModel()
    data class Error(var error: Int) : CharacterUIModel()
    data class Success(val data: List<CharacterModel>) : CharacterUIModel()
}