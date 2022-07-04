package com.example.tictactoer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserStepDto(
    var x: Int,
    var y: Int
)
