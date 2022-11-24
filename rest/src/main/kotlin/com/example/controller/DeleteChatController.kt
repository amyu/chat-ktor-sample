package com.example.controller

import com.example.Database
import com.example.domain.ChatId
import com.example.domain.RestException
import kotlinx.serialization.Serializable

object DeleteChatController {
    fun execute(
        database: Database,
        request: PostDeleteChatRequest
    ): PostDeleteChatResponse {
        val chatRoomId = database.chat.filter { it.value.any { it.id == request.chatId } }.keys.firstOrNull() ?: RestException.NotFoundChatRoom
        database.chat[chatRoomId]?.removeIf { it.id == request.chatId }  ?: RestException.NotFoundChatRoom
        return PostDeleteChatResponse(
            chatId = request.chatId
        )
    }
}

@Serializable
data class PostDeleteChatRequest(
    val chatId: ChatId
)

@Serializable
data class PostDeleteChatResponse(
    val chatId: ChatId
)