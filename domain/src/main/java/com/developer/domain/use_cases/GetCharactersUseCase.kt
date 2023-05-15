package com.developer.domain.use_cases

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseUseCase<Unit, Flow<List<CharacterEntityItem>>> {

    override suspend operator fun invoke(params: Unit) = characterRepository.getCharacters()
}