package com.example.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
sealed interface Chat {
    val id: ChatId
    val createdAt: LocalDateTime
    val likedUsers: List<User>
    val sendUser:User

    companion object {
        fun generateId(): String = UUID.randomUUID().toString()
    }
}

typealias ChatId = String

@Serializable
data class TextChat(
    override val id: ChatId,
    override val createdAt: LocalDateTime,
    override val likedUsers: List<User>,
    override val sendUser: User,
    val message: String,
) : Chat

@Serializable
data class ImageChat(
    override val id: ChatId,
    override val createdAt: LocalDateTime,
    override val likedUsers: List<User>,
    override val sendUser: User,
    val imageUrl: String
) : Chat