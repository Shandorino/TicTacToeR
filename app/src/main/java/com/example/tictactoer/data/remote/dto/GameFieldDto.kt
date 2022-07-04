package com.example.tictactoer.data.remote.dto

import com.google.gson.JsonElement

data class GameFieldDto(
    var _gameField: String,
    var nowPlayer: JsonElement?,
    var message: String?
)
