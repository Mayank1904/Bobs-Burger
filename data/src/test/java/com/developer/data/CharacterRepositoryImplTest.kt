package com.developer.data

import com.developer.data.mappers.CharacterEntityItemMapper
import com.developer.data.mappers.CharacterEntityMapper
import com.developer.data.models.CharacterEntityItemModel
import com.developer.data.models.Relative
import com.developer.data.remote.api.CharacterService
import com.developer.data.repository.CharacterRepositoryImpl
import com.developer.data.utils.BaseDataTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryImpTest : BaseDataTest() {


    @Mock
    lateinit var characterItemMapper: CharacterEntityItemMapper
    @Mock
    lateinit var characterMapper : CharacterEntityMapper

    @Mock
    lateinit var characterService : CharacterService

    lateinit var characterRepositoryImpl : CharacterRepositoryImpl

    @Before
    fun setUp() {
        characterRepositoryImpl = CharacterRepositoryImpl(characterService,characterMapper, characterItemMapper)
    }

    @Test
    fun `get characters should return character list`() =
        dispatcher.runBlockingTest {
            `when`(characterService.getCharacters()) doReturn getCharacters()

            // Act (When)
            val characters = characterRepositoryImpl.getCharacters().singleOrNull()

            // Assert (Then)
            TestCase.assertEquals(characters?.size, 0)
            verify(characterMapper, times(1)).mapFromModel(any())
//            verify(characterItemMapper, times(1)).mapFromModel(any())

        }

    @Test
    fun `get character by id should return character`() =
        dispatcher.runBlockingTest {
            val characterId = 448
//            val mockCharacter = mock<CharacterEntityItemModel>()
//            `when`(mockCharacter.image) doReturn ""

            `when`(characterService.getCharacter(characterId)) doReturn getCharacter()

            // Act (When)
            val character = characterRepositoryImpl.getCharacter(characterId).singleOrNull()

            // Assert (Then)
            assertEquals(character?.id, null)
            verify(characterItemMapper, times(1)).mapFromModel(any())
//            verify(characterItemMapper, times(1)).mapFromModel(any())

        }

    private fun getCharacters(): List<CharacterEntityItemModel> =
        listOf(
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

    private fun getCharacter(): CharacterEntityItemModel = CharacterEntityItemModel(
                id = 448,
                age = "12",
                name = "",
                image = "",
                gender = "",
                hairColor = "Black",
                occupation = "",
                firstEpisode = "",
                voicedBy = "",
                url = "",
                wikiUrl = "",
                relatives = listOf(
                    Relative(
                        id = "",
                        name = "",
                        relationship = "",
                        url = "",
                        wikiUrl = ""
                    )
                )
            )
}