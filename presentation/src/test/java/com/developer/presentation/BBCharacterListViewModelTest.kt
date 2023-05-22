package com.developer.presentation

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import com.developer.domain.use_cases.GetCharactersUseCase
import com.developer.presentation.viewmodel.BBCharactersViewModel
import com.developer.presentation.viewmodel.CharacterUIModel
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BBCharacterListViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Mock
    lateinit var charactersUseCase: GetCharactersUseCase


    private lateinit var viewModel: BBCharactersViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = BBCharactersViewModel(charactersUseCase)
    }

    @Test
    fun `get characters should return character list from use-case`() =
        runTest {
            // Arrange (Given)
            val characters = getCharacters()
            `when`(charactersUseCase(Unit)).thenReturn(flowOf(characters))
            Assert.assertEquals(CharacterUIModel.Loading, viewModel.characterListFlow.value)

            // Act (When)
            viewModel.loadCharacters()

            // Assert (Then)
            Assert.assertEquals(CharacterUIModel.Success(characters), viewModel.characterListFlow.value)

        }

    @Test
    fun `get characters should return empty character list from use-case`() =
        runTest {
            // Arrange (Given)
            val characters = listOf<CharacterEntityItem>()
            Assert.assertEquals(CharacterUIModel.Loading, viewModel.characterListFlow.value)

            `when`(charactersUseCase(Unit)).thenReturn(flowOf( characters))
            // Act (When)
            viewModel.loadCharacters()

            // Assert (Then)
            Assert.assertEquals(CharacterUIModel.Success(characters), viewModel.characterListFlow.value)
        }

    @Test
    fun `get characters should return error from use-case`() =
        runTest {
            // Arrange (Given)
            val errorMessage = "Internal server error"
            whenever(charactersUseCase(Unit)) doAnswer { throw IOException(errorMessage) }

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

    @After
    fun tearDown() {
    }

}