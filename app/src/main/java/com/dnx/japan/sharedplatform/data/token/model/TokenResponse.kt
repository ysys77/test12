package com.dnx.japan.sharedplatform.data.token.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("id_token_type")
    val idTokenType: String,
    @SerializedName("resource")
    val resource: List<String>
)