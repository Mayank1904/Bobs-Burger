package com.developer.domain.usecases

import com.developer.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(params: Int) = characterRepository.getCharacter(params)
}