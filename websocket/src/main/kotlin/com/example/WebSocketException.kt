package com.example

import com.example.domain.DomainException
import kotlinx.serialization.Serializable

@Serializable
sealed class WebSocketException(override val message: String) : DomainException, Exception(message) {
    object NotSupportedFormat : WebSocketException("送信されたRequestが正しいFormatではありません")
}