package com.developer.presentation.mappers

import com.developer.domain.models.CharacterEntityItem
import com.developer.presentation.models.CharacterModel
import javax.inject.Inject

class CharacterMapper @Inject constructor() :
    Mapper<List<CharacterEntityItem>, List<CharacterModel>> {

    override fun mapFromModel(model: List<CharacterEntityItem>): List<CharacterModel> {
        return model.map {
            CharacterModel(
                firstEpisode = it.firstEpisode,
                gender = it.gender,
                hairColor = it.hairColor,
                image = it.image,
                name = it.name,
                occupation = it.occupation,
                voicedBy = it.voicedBy,
                id = it.id,
            )
        }
    }
}