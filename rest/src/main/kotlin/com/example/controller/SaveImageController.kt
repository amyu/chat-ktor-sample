package com.example.controller

import io.ktor.http.content.*
import kotlinx.serialization.Serializable
import java.io.File

object SaveImageController {
    suspend fun execute(
        multipartData: MultiPartData,
    ): SaveImageResponse {
        val dir = File("uploads")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        var fileName = ""
        multipartData.forEachPart { part ->
            if (part is PartData.FileItem) {
                fileName = part.originalFileName as String
                val file = File("uploads/$fileName")

                if (!file.exists()) {
                    val fileBytes = part.streamProvider().readBytes()
                    File("uploads/$fileName").writeBytes(fileBytes)
                }
            }
            part.dispose()
        }

        val imageUrl = "localhost:8080/uploads/${fileName}"

        return SaveImageResponse(
            uploadedImageUrl = imageUrl
        )
    }
}

@Serializable
data class SaveImageResponse(
    val uploadedImageUrl: String
)