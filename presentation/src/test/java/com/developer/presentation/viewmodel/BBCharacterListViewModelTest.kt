package com.developer.presentation.viewmodel

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import com.developer.domain.repository.CharacterRepository
import com.developer.domain.usecases.GetCharactersUseCase
import com.developer.presentation.MainDispatcherRule
import com.developer.presentation.helper.BaseViewModelTest
import com.developer.presentation.mappers.CharacterMapper
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class BBCharacterListViewModelTest : BaseViewModelTest() {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var characterRepository: CharacterRepository

    lateinit var charactersUseCase: GetCharactersUseCase

    lateinit var characterMapper: CharacterMapper

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
        dispatcher.runBlockingTest {
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
        dispatcher.runBlockingTest {
            // Arrange (Given)
            val characters = listOf<CharacterEntityItem>()
            `when`(charactersUseCase(limit)).thenReturn(flowOf(characters))
            // Act (When)
            viewModel.loadCharacters()
            // Assert (Then)
//            Assert.assertEquals(CharacterUIModel.Loading, viewModel.characterListFlow.value)
            Assert.assertEquals(
                CharacterUIModel.Success(characterMapper.mapFromModel(characters)),
                viewModel.characterListFlow.value
            )
        }

    @Test
    fun `get characters should return error from use-case`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            val errorMessage = "Internal server error"
            `when`(charactersUseCase(limit)) doAnswer { throw IllegalAccessException(errorMessage) }

            // Act (When)
            viewModel.loadCharacters()

            // Assert (Then)
            Assert.assertEquals(CharacterUIModel.Loading, viewModel.characterListFlow.value)
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
            wikiUrl = "",
            relatives = listOf(
                Relative(id = "", name = "", relationship = "", url = "", wikiUrl = "")
            )
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
            wikiUrl = "",
            relatives = listOf(
                Relative(id = "", name = "", relationship = "", url = "", wikiUrl = "")
            )
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
            wikiUrl = "",
            relatives = listOf(
                Relative(id = "", name = "", relationship = "", url = "", wikiUrl = "")
            )
        )
    )

    companion object {
        const val limit = 30
    }

}