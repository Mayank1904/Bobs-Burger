package com.developer.data

import com.developer.data.mappers.CharacterEntityItemMapper
import com.developer.data.mappers.CharacterEntityMapper
import com.developer.data.models.CharacterEntityItemModel
import com.developer.data.remote.CharacterService
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
import kotlinx.coroutines.test.runTest
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
    lateinit var characterMapper: CharacterEntityMapper

    @Mock
    lateinit var characterService: CharacterService

    private lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Before
    fun setUp() {
        characterRepositoryImpl =
            CharacterRepositoryImpl(characterService, characterMapper, characterItemMapper)
    }

    @Test
    fun `get characters should return character list`() =
        runTest {
            `when`(characterService.getCharacters(LIMIT)) doReturn getCharacters()

            // Act (When)
            val characters = characterRepositoryImpl.getCharacters(LIMIT).singleOrNull()

            // Assert (Then)
            TestCase.assertEquals(characters?.size, 0)
            verify(characterMapper, times(1)).mapFromModel(any())
        }

    @Test
    fun `get character by id should return character`() =
        runTest {
            `when`(characterService.getCharacter(CHARACTER_ID)) doReturn getCharacter()

            // Act (When)
            val character = characterRepositoryImpl.getCharacter(CHARACTER_ID).singleOrNull()

            // Assert (Then)
            assertEquals(character?.id, null)
            verify(characterItemMapper, times(1)).mapFromModel(any())
        }

    private fun getCharacters(): List<CharacterEntityItemModel> =
        listOf(
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

    private fun getCharacter(): CharacterEntityItemModel = CharacterEntityItemModel(
        id = CHARACTER_ID,
        age = AGE,
        name = EMPTY_STRING,
        image = EMPTY_STRING,
        gender = EMPTY_STRING,
        hairColor = HAIR_COLOR,
        occupation = EMPTY_STRING,
        firstEpisode = EMPTY_STRING,
        voicedBy = EMPTY_STRING,
        url = EMPTY_STRING,
        wikiUrl = EMPTY_STRING,
    )

    companion object {
        private const val CHARACTER_ID = 441
        private const val LIMIT = 5
        private const val EMPTY_STRING = ""
        private const val AGE = "12"
        private const val HAIR_COLOR = "Black"
    }
}