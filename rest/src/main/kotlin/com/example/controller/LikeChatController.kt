package com.example.controller

import com.example.Database
import com.example.domain.ImageChat
import com.example.domain.RestException
import com.example.domain.TextChat
import com.example.domain.User
import kotlinx.serialization.Serializable

object LikeChatController {
    fun execute(
        database: Database,
        request: PostLikeChatRequest,
    ): PostLikeChatResponse {
        val likedUser = database.users.find { it.id == request.likedUserId } ?: throw RestException.NotFoundUser
        val likedUsers :MutableList<User> = mutableListOf()
        database.chat[request.roomId]?.replaceAll {
            if (it.id == request.chatId) {
                likedUsers.addAll(it.likedUsers + likedUser)
                when (it) {
                    is TextChat -> {
                        it.copy(likedUsers = likedUsers)
                    }
                    is ImageChat -> {
                        it.copy(likedUsers = likedUsers)
                    }
                }
            } else {
                it
            }
        } ?: throw RestException.NotFoundChatRoom

        return PostLikeChatResponse(
            chatId = request.chatId,
            likedUsers = likedUsers,
        )
    }
}

@Serializable
data class PostLikeChatRequest(
    val roomId: String,
    val likedUserId: String,
    val chatId: String,
)

@Serializable
data class PostLikeChatResponse(
    val chatId: String,
    val likedUsers: List<User>,
)