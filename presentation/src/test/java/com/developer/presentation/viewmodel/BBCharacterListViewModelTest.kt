package com.developer.presentation.viewmodel

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.repository.CharacterRepository
import com.developer.domain.usecases.GetCharactersUseCase
import com.developer.presentation.MainDispatcherRule
import com.developer.presentation.mappers.CharacterMapper
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class BBCharacterListViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var characterRepository: CharacterRepository

    lateinit var charactersUseCase: GetCharactersUseCase

    private lateinit var characterMapper: CharacterMapper

    private lateinit var viewModel: BBCharactersViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        charactersUseCase = GetCharactersUseCase(characterRepository)
        characterMapper = CharacterMapper()
        viewModel = BBCharactersViewModel(charactersUseCase, characterMapper)
    }

    @Test
    fun `get characters should return character list from use-case`() =
        runTest {
            // Arrange (Given)
            val characters = getCharacters()
            whenever(charactersUseCase(LIMIT)).thenReturn(flowOf(characters))
            // Act (When)
            viewModel.loadCharacters()
            // Assert (Then)
            Assert.assertEquals(
                CharacterUIModel.Success(characterMapper.mapFromModel(characters)),
                viewModel.characterListFlow.value
            )
        }

    @Test
    fun `get characters should return empty character list from use-case`() =
        runTest {
            // Arrange (Given)
            val characters = listOf<CharacterEntityItem>()
            `when`(charactersUseCase(LIMIT)).thenReturn(flowOf(characters))
            // Act (When)
            viewModel.loadCharacters()
            // Assert (Then)
            Assert.assertEquals(
                CharacterUIModel.Success(characterMapper.mapFromModel(characters)),
                viewModel.characterListFlow.value
            )
        }

    @Test
    fun `get characters should return error from use-case`() =
        runTest {
            // Arrange (Given)
            `when`(charactersUseCase(LIMIT)) doAnswer { throw IOException(ERROR_MESSAGE) }
            Assert.assertEquals(CharacterUIModel.Loading, viewModel.characterListFlow.value)
            // Act (When)
            viewModel.loadCharacters()
        }

    private fun getCharacters(): List<CharacterEntityItem> = listOf(
        CharacterEntityItem(
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
        CharacterEntityItem(
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
        CharacterEntityItem(
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

    companion object {
        private const val CHARACTER_ID = 441
        private const val LIMIT = 30
        private const val ERROR_MESSAGE = "Internal Server Error"
        private const val EMPTY_STRING = ""
        private const val AGE = "12"
    }

}