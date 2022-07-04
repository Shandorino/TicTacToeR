package com.example.tictactoer.data.remote.dto


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PostResponseDto(
    var action: String,
    var payload: JsonElement? = null,
    var status: String? = null)
