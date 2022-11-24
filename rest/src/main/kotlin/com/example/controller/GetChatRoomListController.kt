package com.example.controller

import com.example.Database
import com.example.domain.ChatRoom
import kotlinx.serialization.Serializable

object GetChatRoomListController {
    fun execute(
        database: Database,
    ): GetChatRoomListResponse {
        return GetChatRoomListResponse(
            chatRoomList = database.chatRooms
        )
    }
}

@Serializable
data class GetChatRoomListResponse(
    val chatRoomList: List<ChatRoom>
)