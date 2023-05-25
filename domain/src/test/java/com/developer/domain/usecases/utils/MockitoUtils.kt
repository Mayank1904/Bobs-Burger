package com.developer.domain.usecases.utils

import org.mockito.Mockito

inline fun <reified T> mock() = Mockito.mock(T::class.java)!!