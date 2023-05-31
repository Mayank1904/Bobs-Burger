package com.developer.presentation.models


data class CharacterModel(
    val age: String = "",
    val firstEpisode: String,
    val gender: String,
    val hairColor: String,
    val id: Int = Int.MIN_VALUE,
    val image: String,
    val name: String,
    val occupation: String,
    val voicedBy: String,
)