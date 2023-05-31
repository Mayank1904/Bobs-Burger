package com.developer.presentation.mappers

import com.developer.domain.models.CharacterEntityItem
import com.developer.presentation.models.CharacterModel
import javax.inject.Inject

class CharacterItemMapper @Inject constructor(
) : Mapper<CharacterEntityItem, CharacterModel> {
    override fun mapFromModel(model: CharacterEntityItem): CharacterModel {
        return CharacterModel(
            id = model.id,
            firstEpisode = model.firstEpisode,
            gender = model.gender,
            hairColor = model.hairColor,
            image = model.image,
            name = model.name,
            occupation = model.occupation,
            voicedBy = model.voicedBy,
            age = model.age
        )
    }
}