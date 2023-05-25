package com.developer.presentation.helper

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {
    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = CoroutineTestRule()

    val dispatcher = testRule.dispatcher
}