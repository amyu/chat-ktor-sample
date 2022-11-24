package com.example.controller

import com.example.Database
import com.example.domain.RestException
import com.example.domain.User
import kotlinx.serialization.Serializable

object CreateUserController {
    fun execute(
        database: Database,
        request: PostUserRequest
    ): PostUserResponse {
        val isAlreadyCreate = database.users.any { it.name == request.userName }
        if (isAlreadyCreate) {
            throw RestException.AlreadyUser
        }

        val newUser = User(
            id = User.generateId(),
            name = request.userName
        )
        database.users.add(newUser)

        return PostUserResponse(
            userId = newUser.id
        )
    }
}

@Serializable
data class PostUserRequest(val userName: String)

@Serializable
data class PostUserResponse(
    val userId: String
)