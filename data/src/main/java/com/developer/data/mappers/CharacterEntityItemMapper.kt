package com.developer.data.mappers

import com.developer.data.models.CharacterEntityItemModel
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import javax.inject.Inject

class CharacterEntityItemMapper @Inject constructor(
) : EntityMapper<CharacterEntityItemModel, CharacterEntityItem> {
    override fun mapFromModel(model: CharacterEntityItemModel): CharacterEntityItem {
        return CharacterEntityItem(
            id = model.id,
            age = model.age,
            firstEpisode = model.firstEpisode,
            gender = model.gender,
            hairColor = model.hairColor,
            image = model.image,
            name = model.name,
            occupation = model.occupation,
            relatives = model.relatives.map {
                Relative(
                    id = it.id,
                    name = it.name,
                    relationship = it.relationship,
                    url = it.url,
                    wikiUrl = it.wikiUrl
                )
            },
            url = model.url,
            voicedBy = model.voicedBy,
            wikiUrl = model.wikiUrl,
        )
    }


}