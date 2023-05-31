package com.developer.domain.models

data class CharacterEntityItem(
    val id: Int,
    val age: String = "",
    val firstEpisode: String,
    val gender: String,
    val hairColor: String,
    val image: String,
    val name: String,
    val occupation: String,
    val url: String,
    val voicedBy: String,
    val wikiUrl: String
)