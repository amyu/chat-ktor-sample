package com.example.domain

import kotlinx.serialization.Serializable

interface DomainException {
    val message:String
}

@Serializable
sealed class RestException(override val message: String) : DomainException, Exception(message) {
    object NotFoundUser : RestException("ユーザが見つかりませんでした")
    object AlreadyUser : RestException("同名のユーザがすでに設定されています")
    object NotFoundChatRoom : RestException("チャットルームが見つかりませんでした")
    object NotFoundChat : RestException("チャットが見つかりませんでした")
    object AlreadyChatRoom : RestException("同名のチャットルームがすでに設定されています")
}