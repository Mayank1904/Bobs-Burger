package com.developer.data.mappers

import com.developer.data.models.CharacterEntityItemModel
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import javax.inject.Inject

class CharacterEntityMapper @Inject constructor() : EntityMapper<List<CharacterEntityItemModel>, List<CharacterEntityItem>> {

    override fun mapFromModel(model: List<CharacterEntityItemModel>): List<CharacterEntityItem> {
        return model.map {
            CharacterEntityItem(
            age = it.age,
            firstEpisode = it.firstEpisode,
            gender = it.gender,
            hairColor = it.hairColor,
            image = it.image,
            name = it.name,
            occupation = it.occupation,
            relatives = it.relatives.map { it -> Relative(id = it.id, name = it.name, relationship = it.relationship, url = it.url, wikiUrl = it.wikiUrl) },
            url = it.url,
            voicedBy = it.voicedBy,
            wikiUrl = it.wikiUrl,
            id = it.id,
        )}
    }
}