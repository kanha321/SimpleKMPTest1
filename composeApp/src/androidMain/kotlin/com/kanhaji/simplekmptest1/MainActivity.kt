package com.kanhaji.simplekmptest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.kanhaji.simplekmptest1.networking.JournalClient
import com.kanhaji.simplekmptest1.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                client = remember {
                    JournalClient(createHttpClient(OkHttp.create()))
                }
            )
        }
    }
}