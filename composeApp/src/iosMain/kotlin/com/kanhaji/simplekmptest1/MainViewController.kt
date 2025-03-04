package com.kanhaji.simplekmptest1

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.kanhaji.simplekmptest1.networking.JournalClient
import com.kanhaji.simplekmptest1.networking.createHttpClient
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    App(
        client = remember {
            JournalClient(createHttpClient(Darwin.create()))
        }
    )
}