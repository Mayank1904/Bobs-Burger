package com.developer.data.mappers

import com.developer.data.models.CharacterEntityItemModel
import com.developer.data.utils.BaseDataTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterEntityMapperTest : BaseDataTest() {

    private lateinit var characterEntityMapperTest: CharacterEntityMapper

    @Before
    fun setUp() {
        characterEntityMapperTest = CharacterEntityMapper()
    }

    @Test
    fun `map  character entity item should return converted character entity item`() =
        dispatcher.run {
            // Arrange (Given)
            val characters = getCharacters()
            // Act (When)

            val character = characterEntityMapperTest.mapFromModel(characters)

            // Assert (Then)
            assertEquals(character[0].id, characterId)
            assertEquals(character[0].age, age)
            assertEquals(character[0].gender, gender)

        }

    private fun getCharacters(): ArrayList<CharacterEntityItemModel> {
        return arrayListOf(
            CharacterEntityItemModel(
                id = 441,
                age = "12",
                name = "",
                image = "",
                gender = "Male",
                hairColor = "",
                occupation = "",
                firstEpisode = "",
                voicedBy = "",
                url = "",
                wikiUrl = ""
            ),
            CharacterEntityItemModel(
                id = 441,
                age = "12",
                name = "",
                image = "",
                gender = "",
                hairColor = "",
                occupation = "",
                firstEpisode = "",
                voicedBy = "",
                url = "",
                wikiUrl = ""
            ),
            CharacterEntityItemModel(
                id = 441,
                age = "12",
                name = "",
                image = "",
                gender = "",
                hairColor = "",
                occupation = "",
                firstEpisode = "",
                voicedBy = "",
                url = "",
                wikiUrl = ""
            )
        )
    }

    companion object {
        private const val characterId: Int = 441
        private const val age: String = "12"
        private const val gender: String = "Male"
    }
}