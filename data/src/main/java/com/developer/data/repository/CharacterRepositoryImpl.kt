package com.developer.data.repository

import com.developer.data.mappers.CharacterEntityItemMapper
import com.developer.data.mappers.CharacterEntityMapper
import com.developer.data.remote.api.CharacterService
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterService: CharacterService,
                                                  private val characterEntityMapper: CharacterEntityMapper,
                                                  private val characterEntityItemMapper: CharacterEntityItemMapper) : CharacterRepository {
    override suspend fun getCharacters(): Flow<List<CharacterEntityItem>> =
        flow {
            val characters = characterService.getCharacters()
            emit(characterEntityMapper.mapFromModel(characters))
        }


    override suspend fun getCharacter(characterId: Int): Flow<CharacterEntityItem> = flow {
        val character = characterService.getCharacter(characterId)
        emit(characterEntityItemMapper.mapFromModel(character))
    }
}