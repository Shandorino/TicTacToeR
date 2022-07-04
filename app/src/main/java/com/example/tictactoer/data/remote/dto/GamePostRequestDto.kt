package com.example.tictactoer.data.remote.dto

import com.google.gson.JsonElement
import kotlinx.serialization.Serializable

@Serializable
data class GamePostRequestDto(
    var action: String,
    var payload: UserStepDto
)
