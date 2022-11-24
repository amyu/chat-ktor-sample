package com.example

import com.example.domain.Chat
import com.example.domain.ChatId
import com.example.domain.ChatRoom
import com.example.domain.User
import kotlinx.datetime.LocalDateTime


class Database {
    val chatRooms: MutableList<ChatRoom> = mutableListOf(
        ChatRoom(
            id = "9aa8f457-8840-413b-8df4-6509537a60e1",
            name = "chatroom 1",
            createdAt = LocalDateTime(1993, 12, 13, 0, 0, 0, 0)
        )
    )
    val chat: MutableMap<ChatId, MutableList<Chat>> = mutableMapOf(
        "9aa8f457-8840-413b-8df4-6509537a60e1" to mutableListOf()
    )
    val users: MutableList<User> = mutableListOf(
        User(
            id = "a54712d6-a488-417b-8cdc-4e9f4038e74c",
            name = "amyu"
        )
    )
}