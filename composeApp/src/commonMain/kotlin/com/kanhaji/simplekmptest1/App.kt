package com.kanhaji.simplekmptest1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kanhaji.simplekmptest1.networking.JournalClient
import com.kanhaji.simplekmptest1.networking.JournalEntry
import com.kanhaji.simplekmptest1.util.NetworkError
import com.kanhaji.simplekmptest1.util.onError
import com.kanhaji.simplekmptest1.util.onSuccess
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import simplekmptest1.composeapp.generated.resources.Res
import simplekmptest1.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(client: JournalClient) {
    MaterialTheme {
        var responseText by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<NetworkError?>(null) }
        val scope = rememberCoroutineScope()

        val journals by remember { mutableStateOf(mutableListOf<JournalEntry>()) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = null

                        client.getJournals()
                            .onSuccess {
                                responseText = it.toString()
                                journals.clear()
                                for (journal in it) {
                                    journals.add(journal)
                                }
                                responseText = getResponseText(journals)
//                                println("123Journals: $journals")
                            }
                            .onError {
                                errorMessage = it
                            }
                        isLoading = false;
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 1.dp,
                        color = Color.White
                    )
                } else {
                    Text("Journals")
                }
//                responseText?.let {
//                    Text(it)
//                }
                errorMessage?.let {
                    Text(
                        text = it.toString(),
                        color = Color.Red
                    )
                }
            }
            Text(
                text = responseText ?: "",
            )
        }
    }
}

fun getResponseText(journals: List<JournalEntry>): String {
    var responseText = ""
    for (journal in journals) {
        responseText += "Journal ${journal.id}\nTitle: ${journal.title}\nContent: ${journal.content}\nCreated: ${journal.created}\n\n"
    }
    return responseText
}