package com.example.tictactoer.data.remote.dto

import com.google.gson.JsonElement

data class SessionStateDto(
    var session: JsonElement,
    var state: String
)
