package com.developer.data.remote.api

import com.developer.data.models.CharacterEntityItemModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("characters")
    suspend fun getCharacters(@Query("limit") limit: Int): List<CharacterEntityItemModel>

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterEntityItemModel
}