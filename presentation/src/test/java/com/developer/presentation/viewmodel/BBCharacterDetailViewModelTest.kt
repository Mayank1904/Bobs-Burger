package com.developer.presentation.viewmodel

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
            `when`(characterByIdUseCase(CHARACTER_ID)).thenReturn(flowOf(character))
            // Act (When)
            viewModel.getCharacterDetail(CHARACTER_ID)

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
            whenever(characterByIdUseCase(CHARACTER_ID)) doAnswer { throw IOException(ERROR_MESSAGE) }
            // Assert (Then)
            viewModel.getCharacterDetail(CHARACTER_ID)
            Assert.assertEquals(
                CharacterDetailUIModel.Loading,
                viewModel.characterDetailFlow.value
            )
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
        private const val CHARACTER_ID: Int = 441
        private const val ERROR_MESSAGE = "Internal Server Error"
        private const val EMPTY_STRING = ""
        private const val AGE = "12"
    }

}