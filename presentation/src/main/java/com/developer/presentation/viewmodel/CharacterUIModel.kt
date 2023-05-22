package com.developer.presentation.viewmodel

import com.developer.domain.models.CharacterEntityItem

sealed class CharacterUIModel {
    object Loading : CharacterUIModel()
    data class Error(var error: Int) : CharacterUIModel()
    data class Success(val data: List<CharacterEntityItem>) : CharacterUIModel()
}