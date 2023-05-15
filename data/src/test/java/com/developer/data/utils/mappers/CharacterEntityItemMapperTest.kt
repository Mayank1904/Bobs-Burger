package com.developer.data.utils.mappers

import com.developer.data.mappers.CharacterEntityItemMapper
import com.developer.data.models.CharacterEntityItemModel
import com.developer.data.models.Relative
import com.developer.data.utils.BaseDataTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterEntityItemMapperTest : BaseDataTest() {
    private lateinit var characterEntityItemMapper:  CharacterEntityItemMapper

    @Before
    fun setUp(){
        characterEntityItemMapper = CharacterEntityItemMapper()
    }

    @Test
    fun `map  character entity item should return converted character entity item`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            val character = getCharacters()[0]
            // Act (When)

            val mappedCharacter = characterEntityItemMapper.mapFromModel(character)

            // Assert (Then)
            assertEquals(mappedCharacter.id, 441)
            assertEquals(mappedCharacter.age, "12")
            assertEquals(mappedCharacter.gender, "Male")

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
}