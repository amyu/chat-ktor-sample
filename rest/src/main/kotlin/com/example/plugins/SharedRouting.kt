package com.example.plugins

import com.example.Database
import com.example.DateTimeProvider
import com.example.controller.*
import com.example.domain.RestException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.sharedRouting(
    database: Database,
    dateTimeProvider: DateTimeProvider,
) {
    routing {
        static("uploads") {
            files("uploads")
        }
        route("/uploadImage") {
            post {
                /**
                 * curl -X POST -F file1=@/Users/amyu/Desktop/Screenshot_20220428_191328.png localhost:8080/uploadImage
                 */
                try {
                    val multipartData = call.receiveMultipart()
                    val response =
                        SaveImageController.execute(multipartData)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
        }

        route("/user") {
            post {
                /**
                 * curl -X POST -H "Content-Type: application/json" -d '{"userName":"fuga"}' localhost:8080/user
                 */
                try {
                    val parameter = call.receive<PostUserRequest>()
                    val response = CreateUserController.execute(database, parameter)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
        }

        route("/chatroom") {
            post {
                /**
                 * curl -X POST -H "Content-Type: application/json" -d '{"roomName":"fuga"}' localhost:8080/chatroom
                 */
                try {
                    val parameter = call.receive<PostChatRoomRequest>()
                    val response = CreateChatRoomController.execute(database, dateTimeProvider, parameter)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
            get("/list") {
                /**
                 * curl -v localhost:8080/chatroom/list
                 */
                try {
                    val response = GetChatRoomListController.execute(database)
                    call.respond(response)
                } catch (e: RestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message)
                }
            }
            route("{chatRoomId?}") {
                get {
                    /**
                     * curl -v localhost:8080/chatroom/:chatRoomId
                     */
                    try {
                        val chatRoomId = call.parameters["chatRoomId"]!!
                        val response = GetChatRoomController.execute(database, chatRoomId)
                        call.respond(response)
                    } catch (e: RestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message)
                    }
                }
                delete {
                    /**
                     *  curl -X DELETE localhost:8080/chatroom/:chatRoomId
                     */
                    try {
                        val chatRoomId = call.parameters["chatRoomId"]!!
                        val response = DeleteChatRoomController.execute(database, chatRoomId)
                        call.respond(response)
                    } catch (e: RestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message)
                    }
                }
            }
        }
    }
}
