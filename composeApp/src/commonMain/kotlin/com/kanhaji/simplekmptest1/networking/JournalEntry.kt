package com.kanhaji.simplekmptest1.networking

import kotlinx.serialization.Serializable

@Serializable
data class JournalEntry(
    var id: String,
    var title: String,
    var content: String,
    var created: Long
)
