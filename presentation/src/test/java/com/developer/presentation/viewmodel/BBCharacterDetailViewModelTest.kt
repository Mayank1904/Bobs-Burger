package com.developer.presentation.viewmodel

import android.content.Context
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.repository.CharacterRepository
import com.developer.domain.usecases.GetCharacterByIdUseCase
import com.developer.presentation.MainDispatcherRule
import com.developer.presentation.mappers.CharacterItemMapper
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
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BBCharacterDetailViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var characterRepository: CharacterRepository

    lateinit var characterByIdUseCase: GetCharacterByIdUseCase

    lateinit var characterItemMapper: CharacterItemMapper

    private lateinit var viewModel: BBCharacterDetailViewModel


    @Before
    fun setup() {
        characterByIdUseCase = GetCharacterByIdUseCase(characterRepository)
        characterItemMapper = CharacterItemMapper()
        viewModel = BBCharacterDetailViewModel(characterByIdUseCase, characterItemMapper)
    }

    @Test
    fun `get character detail with character Id should return character list from use-case`() =
        runTest {
            // Arrange (Given)
            val character = getCharacters()[0]
            `when`(characterByIdUseCase(characterId)).thenReturn(flowOf(character))
            // Act (When)
            viewModel.getCharacterDetail(characterId)

            val mappedData = characterItemMapper.mapFromModel(character)
            // Assert (Then)
            Assert.assertEquals(
                CharacterDetailUIModel.Success(mappedData),
                viewModel.characterDetailFlow.value
            )

        }

    @Test
    fun `get character detail with character Id should return error from use-case`() =
        runTest {
            // Arrange (Given)
            whenever(characterByIdUseCase(characterId)) doAnswer { throw IOException(errorMessage) }
            // Assert (Then)
            viewModel.getCharacterDetail(characterId)
            Assert.assertEquals(
                CharacterDetailUIModel.Loading,
                viewModel.characterDetailFlow.value
            )
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
        private const val characterId: Int = 441
        private const val errorMessage = "Internal Server Error"

    }

}