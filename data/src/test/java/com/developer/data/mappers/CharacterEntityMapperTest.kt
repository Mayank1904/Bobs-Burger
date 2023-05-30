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
            assertEquals(character[0].id, CHARACTER_ID)
            assertEquals(character[0].age, AGE)
            assertEquals(character[0].gender, GENDER)

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