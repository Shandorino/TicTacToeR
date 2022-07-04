package com.example.tictactoer.data.remote.dto

import com.google.gson.JsonElement

data class SessionInfoDto(
    val firstPlayer: JsonElement,
    var secondPlayer: JsonElement,
    var nowPlayer: JsonElement,
    var status: String
)
