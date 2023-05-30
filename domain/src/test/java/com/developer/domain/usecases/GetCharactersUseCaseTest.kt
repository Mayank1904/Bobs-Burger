package com.developer.domain.usecases

import com.developer.domain.models.CharacterEntityItem
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

    @Test
    fun `get bob characters should return success with bob character list`() =
        dispatcher.runBlockingTest {
            whenever(characterRepository.getCharacters(LIMIT)) doReturn getCharacters()

            val characters = getCharactersUseCase(LIMIT).single()

            assertEquals(characters.size, ACTUAL_SIZE)
            verify(characterRepository, times(1)).getCharacters(LIMIT)
        }


    @Test
    fun `get bob characters should return error result with exception`() =
        dispatcher.runBlockingTest {
            whenever(characterRepository.getCharacters(LIMIT)) doAnswer { throw IOException() }

            // Act (When)
            launch(exceptionHandler) { getCharactersUseCase(LIMIT).single() }

            // Assert (Then)
            assertThat(
                exceptionHandler.uncaughtExceptions.first(),
                instanceOf(IOException::class.java)
            )
            verify(characterRepository, times(NUM_INVOCATIONS)).getCharacters(LIMIT)
        }

    private fun getCharacters(): Flow<List<CharacterEntityItem>> = flow {
        val characters = listOf(
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

        emit(characters)
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val CHARACTER_ID = 441
        private const val AGE = "12"
        private const val LIMIT = 5
        private const val ACTUAL_SIZE = 3
        private const val NUM_INVOCATIONS = 1
    }
}