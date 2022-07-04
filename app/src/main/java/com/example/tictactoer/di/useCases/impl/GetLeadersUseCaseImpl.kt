package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.GetLeadersUseCase
import javax.inject.Inject

class GetLeadersUseCaseImpl @Inject constructor(
    private val socketService: SocketService
): GetLeadersUseCase {

    override suspend fun invoke() = socketService.getLeaders("getLeaders")

}