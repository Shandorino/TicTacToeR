package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.FindPlayerUseCase
import javax.inject.Inject

class FindPlayerUseCaseImpl @Inject constructor(
    private val socketService: SocketService
): FindPlayerUseCase {

    override suspend fun invoke() = socketService.findPlayer("find-player")


}