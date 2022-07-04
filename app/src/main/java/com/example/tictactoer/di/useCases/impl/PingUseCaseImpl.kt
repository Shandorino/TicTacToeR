package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.PingUseCase
import javax.inject.Inject

class PingUseCaseImpl @Inject constructor(
    private val socketService: SocketService
) : PingUseCase {

    override suspend fun invoke() = socketService.sendPing("ping")
}