package com.example.plugins

import com.example.Database
import com.example.DateTimeProvider
import com.example.contoller.ChatWebSocket
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Application.configureRouting() {
    val database = Database()
    val dateTimeProvider = DateTimeProvider()

    val chatWebSocket = ChatWebSocket(database, dateTimeProvider)

    sharedRouting(database, dateTimeProvider)

    routing {
        route("/chatroom") {
            webSocket("{chatRoomId?}") {
                /**
                 * wscat -c localhost:8080/chatroom/9aa8f457-8840-413b-8df4-6509537a60e1
                 */
                val chatRoomId = call.parameters["chatRoomId"]!!
                chatWebSocket.observe(
                    thisConnection = ChatWebSocket.Connection(this),
                    incoming = incoming,
                    outgoing = outgoing,
                    chatRoomId = chatRoomId
                )
            }
        }
    }
}
