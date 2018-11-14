package com.example.rikis.footballmatchscheduleapp.utils

import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}