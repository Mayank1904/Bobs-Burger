package com.developer.presentation.viewmodel

import com.developer.domain.models.CharacterEntityItem

sealed class CharacterState {
    object Init : CharacterState()
    object Loading : CharacterState()
    data class Error(var message: Int) : CharacterState()
    data class Success(var characters: List<CharacterEntityItem>) : CharacterState()

}