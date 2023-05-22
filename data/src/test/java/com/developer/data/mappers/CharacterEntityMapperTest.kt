package com.developer.data.mappers

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
class CharacterEntityMapperTest : BaseDataTest() {

    lateinit var characterEntityMapperTest : CharacterEntityMapper

    @Before
    fun setUp() {
        characterEntityMapperTest = CharacterEntityMapper()
    }

    @Test
    fun `map  character entity item should return converted character entity item`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            val characters = getCharacters()
            // Act (When)

            val character = characterEntityMapperTest.mapFromModel(characters)

            // Assert (Then)
            assertEquals(character[0].id, 441)
            assertEquals(character[0].age, "12")
            assertEquals(character[0].gender, "Male")

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