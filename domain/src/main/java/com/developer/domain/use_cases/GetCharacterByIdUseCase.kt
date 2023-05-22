package com.developer.domain.use_cases

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseUseCase<Int, Flow<CharacterEntityItem>> {

    override suspend operator fun invoke(params: Int) = characterRepository.getCharacter(params)
}