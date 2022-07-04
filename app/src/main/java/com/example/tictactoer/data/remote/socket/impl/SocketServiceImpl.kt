package com.example.tictactoer.data.remote.socket.impl

import android.util.Log
import com.example.tictactoer.data.remote.dto.GamePostRequestDto
import com.example.tictactoer.data.remote.dto.PostRequestDto
import com.example.tictactoer.data.remote.dto.PostResponseDto
import com.example.tictactoer.data.remote.dto.UserStepDto
import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.main.MainApplication
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SocketServiceImpl(
    private val client: HttpClient
): SocketService {

    private var socket: DefaultClientWebSocketSession? = null

    override val action = MutableStateFlow<PostResponseDto>(PostResponseDto("null"))



    override suspend fun initConnection() {
        if (socket == null) {
            socket = client.webSocketSession(method = HttpMethod.Get,
                host = "194.87.214.152",
                port = 9992,
                path = "/socket?vk_user_id=${MainApplication.application.accountService.userId}"
            )
            observeData()
            Log.d("==========>", "initConnection")
        }

    }

    override suspend fun sendPing(message: String) {
        try {
            socket?.sendSerialized(PostRequestDto(message))
        }
        catch (e:Exception){

        }
    }

    override suspend fun getUser(message: String) {
        try {
            socket?.sendSerialized(PostRequestDto(message))
        }
        catch (e:Exception){

        }
    }

    override suspend fun getLeaders(message: String) {
        try {
            socket?.sendSerialized(PostRequestDto(message))
            Log.d("=========>", "getLeaders")
        }
        catch (e:Exception){

        }
    }

    override suspend fun step(x: Int, y: Int) {
        try {
            socket?.sendSerialized(GamePostRequestDto("step", UserStepDto(x, y)))
        }
        catch (e:Exception){

        }
    }

    override suspend fun findPlayer(message: String) {
        try {
            socket?.sendSerialized(PostRequestDto(message))
        }
        catch (e:Exception){

        }
    }

    override suspend fun cancelFind(message: String) {
        try {
            socket?.sendSerialized(PostRequestDto(message))
        }
        catch (e:Exception){

        }
    }

    private fun observeData(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    val root = socket?.receiveDeserialized<PostResponseDto>()
                    if (root != null) {
                        Log.d("!==========!>", root.action)
                    }
                    when (root?.action) {
                        "initApplication" -> {

                            Log.d("!====!======>", root.payload.toString())
                            //sendPing("as")
                        }
                        "ping" -> {
                            //val a = Json.decodeFromString<String>(root.payload.toString())
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                            //emit(a)
                        }
                        "getUser" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        "getLeaders" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        "find-player" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        "cancel-find" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        "step" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        "gameState" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        "exit-opponent" -> {
                            Log.d("!==========>", root.payload.toString())
                            action.emit(root)
                        }
                        else -> {
                            Log.d("!==========>", "asdfg")
                        }
                    }
                }
            }
        }
        catch (e: Exception){
            Log.e("!==========>", e.localizedMessage)
        }
    }


    override suspend fun closeConnection() {
        try {
            socket?.close()
        }
        catch (e:Exception){

        }
    }


}