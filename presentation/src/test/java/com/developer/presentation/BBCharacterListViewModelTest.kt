package com.developer.presentation

import androidx.lifecycle.Observer
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import com.developer.domain.use_cases.GetCharactersUseCase
import com.developer.presentation.utils.BasePresentationTest
import com.developer.presentation.viewmodel.BBCharactersViewModel
import com.developer.presentation.viewmodel.CharacterUIModel
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BBCharacterListViewModelTest : BasePresentationTest() {

    @Mock
    lateinit var charactersUseCase: GetCharactersUseCase


    private lateinit var viewModel: BBCharactersViewModel

    @Mock
    private lateinit var observer: Observer<CharacterUIModel>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = BBCharactersViewModel(charactersUseCase, dispatcher)
        viewModel.getCharacterList().observeForever(observer)
    }

    @Test
    fun `get characters should return character list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val characters = getCharacters()
            `when`(charactersUseCase(Unit)).thenReturn(flowOf(characters))
            // Act (When)
            viewModel.getCharacters()

            // Assert (Then)
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Success(characters))

            }

    @Test
    fun `get characters should return empty character list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val characters = listOf<CharacterEntityItem>()
            `when`(charactersUseCase(Unit)).thenReturn(flowOf( characters))
            // Act (When)
            viewModel.getCharacters()

            // Assert (Then)
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Success(characters))
        }

    @Test
    fun `get characters should return error from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val errorMessage = "Internal server error"
            whenever(charactersUseCase(Unit)) doAnswer { throw IOException(errorMessage) }

            // Act (When)
            viewModel.getCharacters()

            // Assert (Then)
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Error(errorMessage))
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
        viewModel.onCleared()
    }

}