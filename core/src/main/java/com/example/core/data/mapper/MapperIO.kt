package com.example.core.data.mapper

interface MapperIO<I,O> {
    suspend fun map(input: I) : O
}