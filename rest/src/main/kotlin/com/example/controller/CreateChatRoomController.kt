package com.example.controller

import com.example.Database
import com.example.DateTimeProvider
import com.example.domain.ChatRoom
import com.example.domain.RestException
import kotlinx.serialization.Serializable

object CreateChatRoomController {
    fun execute(
        database: Database,
        dateTimeProvider: DateTimeProvider,
        request: PostChatRoomRequest,
    ): PostChatRoomResponse {
        val isAlreadyCreate = database.chatRooms.any { it.name == request.roomName }
        if (isAlreadyCreate) {
            throw RestException.AlreadyChatRoom
        }

        val newChatRoom = ChatRoom(
            id = ChatRoom.generateId(),
            name = request.roomName,
            createdAt = dateTimeProvider.now()
        )
        database.chatRooms.add(newChatRoom)
        database.chat.plus(newChatRoom to emptyList())

        return PostChatRoomResponse(
            chatRoomId = newChatRoom.id
        )
    }
}

@Serializable
data class PostChatRoomRequest(val roomName: String)

@Serializable
data class PostChatRoomResponse(
    val chatRoomId: String
)