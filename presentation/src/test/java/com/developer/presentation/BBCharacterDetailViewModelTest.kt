package com.developer.presentation

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import com.developer.domain.use_cases.GetCharacterByIdUseCase
import com.developer.presentation.viewmodel.BBCharacterDetailViewModel
import com.developer.presentation.viewmodel.CharacterDetailUIModel
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
class BBCharacterDetailViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var characterByIdUseCase: GetCharacterByIdUseCase


    private lateinit var viewModel: BBCharacterDetailViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = BBCharacterDetailViewModel(characterByIdUseCase)
    }

    @Test
    fun `get character detail with character Id should return character list from use-case`() =
        runTest {
            // Arrange (Given)
            val characterId = 448
            val character = getCharacters()[0]
            `when`(characterByIdUseCase(448)).thenReturn(flowOf(character))
            Assert.assertEquals(CharacterDetailUIModel.Loading, viewModel.characterDetailFlow.value)
            // Act (When)
            viewModel.getCharacterDetail(characterId)

            // Assert (Then)
            Assert.assertEquals(CharacterDetailUIModel.Success(character), viewModel.characterDetailFlow.value)

        }

    @Test
    fun `get character detail with character Id should return error from use-case`() =
        runTest {
            val characterId = 448
            // Arrange (Given)
            val errorMessage = "Internal server error"
            Assert.assertEquals(CharacterDetailUIModel.Loading, viewModel.characterDetailFlow.value)

            whenever(characterByIdUseCase(characterId)) doAnswer { throw IOException(errorMessage) }

            // Act (When)
            viewModel.getCharacterDetail(characterId)

            // Assert (Then)
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
//        viewModel.onCleared()
    }

}