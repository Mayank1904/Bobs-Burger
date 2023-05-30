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
class CharacterEntityItemMapperTest : BaseDataTest() {
    private lateinit var characterEntityItemMapper: CharacterEntityItemMapper

    @Before
    fun setUp() {
        characterEntityItemMapper = CharacterEntityItemMapper()
    }

    @Test
    fun `map  character entity item should return converted character entity item`() =
        dispatcher.run {
            // Arrange (Given)
            val character = getCharacters()[0]
            // Act (When)

            val mappedCharacter = characterEntityItemMapper.mapFromModel(character)

            // Assert (Then)
            assertEquals(mappedCharacter.id, CHARACTER_ID)
            assertEquals(mappedCharacter.age, AGE)
            assertEquals(mappedCharacter.gender, GENDER)

        }

    private fun getCharacters(): ArrayList<CharacterEntityItemModel> {
        return arrayListOf(
            CharacterEntityItemModel(
                id = CHARACTER_ID,
                age = AGE,
                name = EMPTY_STRING,
                image = EMPTY_STRING,
                gender = GENDER,
                hairColor = EMPTY_STRING,
                occupation = EMPTY_STRING,
                firstEpisode = EMPTY_STRING,
                voicedBy = EMPTY_STRING,
                url = EMPTY_STRING,
                wikiUrl = EMPTY_STRING
            ),
            CharacterEntityItemModel(
                id = CHARACTER_ID,
                age = AGE,
                name = EMPTY_STRING,
                image = EMPTY_STRING,
                gender = EMPTY_STRING,
                hairColor = EMPTY_STRING,
                occupation = EMPTY_STRING,
                firstEpisode = EMPTY_STRING,
                voicedBy = EMPTY_STRING,
                url = EMPTY_STRING,
                wikiUrl = EMPTY_STRING
            ),
            CharacterEntityItemModel(
                id = CHARACTER_ID,
                age = AGE,
                name = EMPTY_STRING,
                image = EMPTY_STRING,
                gender = EMPTY_STRING,
                hairColor = EMPTY_STRING,
                occupation = EMPTY_STRING,
                firstEpisode = EMPTY_STRING,
                voicedBy = EMPTY_STRING,
                url = EMPTY_STRING,
                wikiUrl = EMPTY_STRING
            )
        )
    }


    companion object {
        private const val CHARACTER_ID = 441
        private const val AGE = "12"
        private const val GENDER = "Male"
        private const val EMPTY_STRING = ""
    }
}