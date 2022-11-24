package com.example.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    val id: String,
    val name: String,
) {
    companion object {
        fun generateId(): String = UUID.randomUUID().toString()
    }
}