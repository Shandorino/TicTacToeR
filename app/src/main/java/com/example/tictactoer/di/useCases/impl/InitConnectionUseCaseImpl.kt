package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.InitConnectionUseCase
import javax.inject.Inject

class InitConnectionUseCaseImpl @Inject constructor(
    private val socketService: SocketService
): InitConnectionUseCase {
    override suspend fun invoke() = socketService.initConnection()
}