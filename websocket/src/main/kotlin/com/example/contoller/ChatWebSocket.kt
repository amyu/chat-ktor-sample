package com.example.contoller

import com.example.Database
import com.example.DateTimeProvider
import com.example.controller.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


class ChatWebSocket(
    private val database: Database,
    private val dateTimeProvider: DateTimeProvider,
) {
    enum class Type {
        Initial, NewMessage, NewImage, Liked, Delete
    }

    private val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())

    suspend fun observe(
        thisConnection: Connection,
        incoming: ReceiveChannel<Frame>,
        outgoing: SendChannel<Frame>,
        chatRoomId: String,
    ) {
        connections += thisConnection

        val initial = GetChatRoomController.execute(database, chatRoomId)
        outgoing.send(Frame.Text(Json.encodeToString(Type.Initial to initial)))
        incoming.consumeEach { frame ->
            if (frame !is Frame.Text) {
                return
            }

            // Text Chatが送られてきたとき
            try {
                val postChatMessageRequest = Json.decodeFromString<PostChatMessageRequest>(frame.readText())
                val response = CreateChatMessageController.execute(database, dateTimeProvider, postChatMessageRequest)
                connections.forEach {
                    it.session.send(Frame.Text(Json.encodeToString(Type.NewMessage to response)))
                }
            } catch (e: SerializationException) {
                // nop
            }

            // Image Chatが送られてきたとき
            try {
                val postChatImageRequest = Json.decodeFromString<PostChatImageRequest>(frame.readText())
                val response = CreateChatImageController.execute(database, dateTimeProvider, postChatImageRequest)
                connections.forEach {
                    it.session.send(Frame.Text(Json.encodeToString(Type.NewImage to response)))
                }
            } catch (e: SerializationException) {
                // nop
            }

            // Like Eventが送られてきたとき
            try {
                val postLikeChatRequest = Json.decodeFromString<PostLikeChatRequest>(frame.readText())
                val response = LikeChatController.execute(database, postLikeChatRequest)
                connections.forEach {
                    it.session.send(Frame.Text(Json.encodeToString(Type.Liked to response)))
                }
            } catch (e: SerializationException) {
                // nop
            }

            // Delete Eventが送られたとき
            try {
                val deleteChatRequest = Json.decodeFromString<PostDeleteChatRequest>(frame.readText())
                val response = DeleteChatController.execute(database, deleteChatRequest)
                connections.forEach {
                    it.session.send(Frame.Text(Json.encodeToString(Type.Delete to response)))
                }
            } catch (e: SerializationException) {
                // nop
            }
        }
    }

    class Connection(val session: DefaultWebSocketSession) {
        companion object {
            val lastId = AtomicInteger(0)
        }

        val name = "user${lastId.getAndIncrement()}"
    }
}