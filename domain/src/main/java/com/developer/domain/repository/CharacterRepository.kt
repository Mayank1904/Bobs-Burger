package com.developer.domain.repository

import com.developer.domain.models.CharacterEntityItem
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(limit: Int): Flow<List<CharacterEntityItem>>
    suspend fun getCharacter(characterId: Int): Flow<CharacterEntityItem>

}