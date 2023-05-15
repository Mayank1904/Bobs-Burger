package com.developer.domain.repository

import com.developer.domain.models.CharacterEntityItem
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): Flow<List<CharacterEntityItem>>
    suspend fun getCharacter(characterId: Int): Flow<CharacterEntityItem>

}