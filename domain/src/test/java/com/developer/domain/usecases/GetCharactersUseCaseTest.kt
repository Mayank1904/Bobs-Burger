package com.developer.domain.usecases

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import com.developer.domain.repository.CharacterRepository
import com.developer.domain.usecases.utils.BaseUseCaseTest
import com.developer.domain.usecases.utils.mock
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterListUseCaseTest : BaseUseCaseTest() {

    private var characterRepository = mock<CharacterRepository>()

    val getCharactersUseCase by lazy { GetCharactersUseCase(characterRepository) }

    @Before
    fun setUp() {
    }

    @Test
    fun `get bob characters should return success with bob character list`() =
        dispatcher.runBlockingTest {
            whenever(characterRepository.getCharacters(5)) doReturn getCharacters()

            val characters = getCharactersUseCase(5).single()

            assertEquals(characters.size, 3)
            verify(characterRepository, times(1)).getCharacters(5)
        }


    @Test
    fun `get bob characters should return error result with exception`() =
        dispatcher.runBlockingTest {
            whenever(characterRepository.getCharacters(5)) doAnswer { throw IOException() }

            // Act (When)
            launch(exceptionHandler) { getCharactersUseCase(5).single() }

            // Assert (Then)
            assertThat(
                exceptionHandler.uncaughtExceptions.first(),
                instanceOf(IOException::class.java)
            )
            verify(characterRepository, times(1)).getCharacters(5)
        }

    private fun getCharacters(): Flow<List<CharacterEntityItem>> = flow {
        val characters = listOf(
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

        emit(characters)
    }
}