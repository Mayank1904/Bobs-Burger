package com.developer.presentation.viewmodel

import android.content.Context
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
    private lateinit var context: Context

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
            whenever(charactersUseCase(limit)).thenReturn(flowOf(characters))
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
            `when`(charactersUseCase(limit)).thenReturn(flowOf(characters))
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
            `when`(charactersUseCase(limit)) doAnswer { throw IOException(errorMessage) }
            Assert.assertEquals(CharacterUIModel.Loading, viewModel.characterListFlow.value)
            // Act (When)
            viewModel.loadCharacters()
        }

    private fun getCharacters(): List<CharacterEntityItem> = listOf(
        CharacterEntityItem(
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
        CharacterEntityItem(
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
        CharacterEntityItem(
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

    companion object {
        private const val limit = 30
        private const val errorMessage = "Internal Server Error"
    }

}