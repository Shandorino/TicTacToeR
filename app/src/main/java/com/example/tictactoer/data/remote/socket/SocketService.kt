package com.example.tictactoer.data.remote.socket

import com.example.tictactoer.data.remote.dto.PostResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SocketService {


    val action: MutableStateFlow<PostResponseDto>

    suspend fun initConnection()

    suspend fun sendPing(message: String)

    suspend fun getUser(message: String)

    suspend fun getLeaders(message: String)

    suspend fun step(x: Int, y: Int)

    suspend fun findPlayer(message: String)

    suspend fun cancelFind(message: String)

    suspend fun closeConnection()

    companion object {

        const val BASE_URL = "194.87.214.152:9992"

    }

    sealed class Endpoints(val url: String) {

        object Socket: Endpoints("$BASE_URL/socket?vk_user_id=")

    }

}