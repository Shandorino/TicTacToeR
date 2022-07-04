package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.GetUserUseCase
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val socketService: SocketService
): GetUserUseCase {

    override suspend fun invoke() = socketService.getUser("getUser")
}