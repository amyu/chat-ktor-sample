package com.example.controller

import com.example.Database
import kotlinx.serialization.Serializable


object DeleteChatRoomController {
    fun execute(
        database: Database,
        chatRoomId: String
    ): DeleteChatRoomResponse {
        database.chatRooms.removeIf { it.id == chatRoomId }
        return DeleteChatRoomResponse(
            chatRoomId = chatRoomId
        )
    }
}

@Serializable
data class DeleteChatRoomResponse(
    val chatRoomId: String
)