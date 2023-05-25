package com.developer.data.mappers

import com.developer.data.models.CharacterEntityItemModel
import com.developer.data.models.Relative
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
            assertEquals(mappedCharacter.id, characterId)
            assertEquals(mappedCharacter.age, age)
            assertEquals(mappedCharacter.gender, gender)

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
                wikiUrl = "",
                relatives = listOf(
                    Relative(id = "1", name = "", relationship = "", url = "", wikiUrl = "")
                )
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
                wikiUrl = "",
                relatives = listOf(
                    Relative(id = "", name = "", relationship = "", url = "", wikiUrl = "")
                )
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
                wikiUrl = "",
                relatives = listOf(
                    Relative(id = "", name = "", relationship = "", url = "", wikiUrl = "")
                )
            )
        )
    }


    companion object {
        private const val characterId: Int = 441
        private const val age: String = "12"
        private const val gender: String = "Male"
    }
}