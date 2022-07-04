package com.example.tictactoer.data.remote.dto

import com.example.tictactoer.data.local.Leader
import kotlinx.serialization.Serializable


@Serializable
data class User(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var rating: Int,
    var photoURL: String
)
