package com.developer.data.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseDataTest {
    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = CoroutineTestRule()

    val dispatcher = testRule.dispatcher
}