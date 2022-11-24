package com.example.controller

import com.example.Database
import com.example.DateTimeProvider
import com.example.domain.Chat
import com.example.domain.RestException
import com.example.domain.TextChat
import kotlinx.serialization.Serializable


object CreateChatMessageController {
    fun execute(
        database: Database,
        dateTimeProvider: DateTimeProvider,
        request: PostChatMessageRequest,
    ): PostChatMessageResponse {
        val sendUser = database.users.find { it.id == request.sendUserId } ?: throw RestException.NotFoundUser
        val newChatMessage = TextChat(
            id = Chat.generateId(),
            createdAt = dateTimeProvider.now(),
            sendUser = sendUser,
            message = request.message,
            likedUsers = emptyList()
        )
        database.chat[request.roomId]?.add(newChatMessage) ?: throw RestException.NotFoundChatRoom

        return PostChatMessageResponse(
            chat = newChatMessage
        )
    }
}

@Serializable
data class PostChatMessageRequest(
    val roomId: String,
    val sendUserId: String,
    val message: String,
)

@Serializable
data class PostChatMessageResponse(
    val chat: TextChat,
)