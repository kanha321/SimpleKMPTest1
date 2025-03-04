package com.kanhaji.simplekmptest1

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kanhaji.simplekmptest1.networking.JournalClient
import com.kanhaji.simplekmptest1.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SimpleKMPTest1",
    ) {
        App(
            client = remember {
                JournalClient(createHttpClient(OkHttp.create()))
            }
        )
    }
}