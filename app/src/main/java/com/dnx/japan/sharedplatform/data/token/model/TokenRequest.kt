package com.dnx.japan.sharedplatform.data.token.model

data class TokenRequest(
    val client_id: String,
    val scope: String,
    val email: String
)