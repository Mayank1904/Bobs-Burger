package com.developer.presentation.mappers

interface Mapper<M, E> {

    fun mapFromModel(model: M): E
}