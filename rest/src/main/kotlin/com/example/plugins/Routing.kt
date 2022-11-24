package com.example.plugins

import com.example.Database
import com.example.DateTimeProvider
import com.example.controller.*
import com.example.domain.RestException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val database = Database()
    val dateTimeProvider = DateTimeProvider()

    sharedRouting(database, dateTimeProvider)

    routing {
        route("/chat") {
            post("/message") {
                /**
                 * curl -X POST -H "Content-Type: application/json" -d '{"roomId":"9aa8f457-8840-413b-8df4-6509537a60e1", "sendUserId":"a54712d6-a488-417b-8cdc-4e9f4038e74c", "message":"first message"}' localhost:8080/chat/message
                 */
                try {
                    val parameter = call.receive<PostChatMessageRequest>()
                    val response = CreateChatMessageController.execute(database, dateTimeProvider, parameter)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
            post("/image") {
                /**
                 * curl -X POST -H "Content-Type: application/json" -F '{"roomId":"9aa8f457-8840-413b-8df4-6509537a60e1", "sendUserId":"a54712d6-a488-417b-8cdc-4e9f4038e74c", "uploadedImageUrl":"localhost:8080/uploads/image.png"}' localhost:8080/chat/image
                 */
                try {
                    val parameter = call.receive<PostChatImageRequest>()
                    val response =
                        CreateChatImageController.execute(database, dateTimeProvider, parameter)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
            post("/like") {
                /**
                 * curl -X POST -H "Content-Type: application/json" -d '{"roomId":"9aa8f457-8840-413b-8df4-6509537a60e1", "likedUserId":"a54712d6-a488-417b-8cdc-4e9f4038e74c", "chatId":"9aa8f457-8840-413b-8df4-6509537a60e1"}' localhost:8080/chat/like
                 */
                try {
                    val parameter = call.receive<PostLikeChatRequest>()
                    val response = LikeChatController.execute(database, parameter)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
            post {
                /**
                 *  curl -X DELETE localhost:8080/chat/9aa8f457-8840-413b-8df4-6509537a60e1
                 */
                try {
                    val parameter = call.receive<PostDeleteChatRequest>()
                    val response = DeleteChatController.execute(database, parameter)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
        }
    }
}
