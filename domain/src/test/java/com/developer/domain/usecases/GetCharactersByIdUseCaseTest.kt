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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterByIdUseCaseTest : BaseUseCaseTest() {

    private var characterRepository = mock<CharacterRepository>()

    lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @Before
    fun setUp() {
        getCharacterByIdUseCase = GetCharacterByIdUseCase(characterRepository)
    }

    @Test
    fun `get bob character by id should return success with bob character`() =
        dispatcher.runBlockingTest {
            whenever(characterRepository.getCharacter(CHARACTER_ID)) doReturn getCharacter()

            val character = getCharacterByIdUseCase(CHARACTER_ID).single()

            assertEquals(character.id, CHARACTER_ID)
            assertEquals(character.hairColor, COLOR)
            verify(characterRepository, times(1)).getCharacter(CHARACTER_ID)
        }


    @Test
    fun `get bob character by id should return error result with exception`() =
        dispatcher.runBlockingTest {
            whenever(characterRepository.getCharacter(CHARACTER_ID)) doAnswer { throw IOException() }

            // Act (When)
            launch(exceptionHandler) { getCharacterByIdUseCase(CHARACTER_ID).single() }

            // Assert (Then)
            assertThat(
                exceptionHandler.uncaughtExceptions.first(),
                instanceOf(IOException::class.java)
            )
            verify(characterRepository, times(1)).getCharacter(CHARACTER_ID)
        }

    private fun getCharacter(): Flow<CharacterEntityItem> = flow {
        val character =
            CharacterEntityItem(
                id = CHARACTER_ID,
                age = AGE,
                name = EMPTY_STRING,
                image = EMPTY_STRING,
                gender = EMPTY_STRING,
                hairColor = COLOR,
                occupation = EMPTY_STRING,
                firstEpisode = EMPTY_STRING,
                voicedBy = EMPTY_STRING,
                url = EMPTY_STRING,
                wikiUrl = EMPTY_STRING
            )

        emit(character)
    }

    companion object {
        private const val CHARACTER_ID = 448
        private const val COLOR = "Black"
        private const val EMPTY_STRING = ""
        private const val AGE = "12"
    }
}