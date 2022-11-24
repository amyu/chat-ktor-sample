package com.example.controller

import com.example.Database
import com.example.DateTimeProvider
import com.example.domain.Chat
import com.example.domain.RestException
import com.example.domain.ImageChat
import kotlinx.serialization.Serializable

object CreateChatImageController {
    fun execute(
        database: Database,
        dateTimeProvider: DateTimeProvider,
        request: PostChatImageRequest,
    ): PostChatImageResponse {
        val sendUser = database.users.find { it.id == request.sendUserId } ?: throw RestException.NotFoundUser

        val newChatImage = ImageChat(
            id = Chat.generateId(),
            createdAt = dateTimeProvider.now(),
            sendUser = sendUser,
            likedUsers = emptyList(),
            imageUrl = request.uploadedImageUrl
        )
        database.chat[request.roomId]?.add(newChatImage) ?: throw RestException.NotFoundChatRoom

        return PostChatImageResponse(
            chat = newChatImage,
        )
    }
}

@Serializable
data class PostChatImageRequest(
    val roomId: String,
    val sendUserId: String,
    val uploadedImageUrl: String
)

@Serializable
data class PostChatImageResponse(
    val chat: ImageChat
)