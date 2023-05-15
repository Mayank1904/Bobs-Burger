package com.developer.data.mappers

interface EntityMapper<M, E> {

    fun mapFromModel(model: M): E
}