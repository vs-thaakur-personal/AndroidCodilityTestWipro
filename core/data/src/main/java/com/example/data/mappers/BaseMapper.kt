package com.example.data.mappers

interface BaseMapper<I, O> {
    fun map(input: I): O
}