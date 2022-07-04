package com.example.tictactoer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostRequestDto(
    var action: String
)
