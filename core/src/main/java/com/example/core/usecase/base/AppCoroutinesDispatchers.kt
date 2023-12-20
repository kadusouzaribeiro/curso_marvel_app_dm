package com.example.core.usecase.base

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutinesDispatchers(
    val io: CoroutineDispatcher,
    val companion: CoroutineDispatcher,
    val main: CoroutineDispatcher
)
