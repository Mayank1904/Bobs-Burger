package com.developer.domain.use_cases

import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.models.Relative
import com.developer.domain.repository.CharacterRepository
import com.developer.domain.use_cases.utils.BaseUseCaseTest
import com.developer.domain.use_cases.utils.mock
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
            val characterId = 448
            whenever(characterRepository.getCharacter(characterId)) doReturn getCharacter()

            val character = getCharacterByIdUseCase(characterId).single()

            assertEquals(character.id, 448)
            assertEquals(character.hairColor, "Black")
            verify(characterRepository, times(1)).getCharacter(characterId)
        }


    @Test
    fun `get bob character by id should return error result with exception`() =
        dispatcher.runBlockingTest {
            val characterId = 448
            whenever(characterRepository.getCharacter(characterId)) doAnswer { throw IOException() }

            // Act (When)
            launch(exceptionHandler) { getCharacterByIdUseCase(characterId).single() }

            // Assert (Then)
            assertThat(
                exceptionHandler.uncaughtExceptions.first(),
                instanceOf(IOException::class.java)
            )
            verify(characterRepository, times(1)).getCharacter(characterId)
        }

    private fun getCharacter(): Flow<CharacterEntityItem> = flow {
        val character =
            CharacterEntityItem(
                id = 448,
                age = "12",
                name = "",
                image = "",
                gender = "",
                hairColor = "Black",
                occupation = "",
                firstEpisode = "",
                voicedBy = "",
                url = "",
                wikiUrl = "",
                relatives = listOf(
                    Relative(id = "", name = "", relationship = "", url = "", wikiUrl = "")
                )
            )

        emit(character)
    }
}