package com.example.controller

import com.example.Database
import com.example.domain.Chat
import com.example.domain.ChatRoom
import com.example.domain.RestException
import kotlinx.serialization.Serializable

object GetChatRoomController {
    fun execute(
        database: Database,
        chatRoomId: String,
    ): GetChatRoomResponse {
        val chatRoom = database.chatRooms.find { it.id == chatRoomId } ?: throw RestException.NotFoundChatRoom
        val chats = database.chat[chatRoomId] ?: emptyList()
        return GetChatRoomResponse(
            chatRoom = chatRoom,
            chats = chats,
        )
    }
}

@Serializable
data class GetChatRoomResponse(
    val chatRoom: ChatRoom,
    val chats: List<Chat>,
)