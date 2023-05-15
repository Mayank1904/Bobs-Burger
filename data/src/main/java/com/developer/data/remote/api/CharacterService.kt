package com.developer.data.remote.api

import com.developer.data.models.CharacterEntityItemModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("characters")
    suspend fun getCharacters(): List<CharacterEntityItemModel>

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterEntityItemModel
}