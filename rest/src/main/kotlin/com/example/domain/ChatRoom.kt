package com.example.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatRoom(
    val id: String,
    val name: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun generateId(): String = UUID.randomUUID().toString()
    }
}