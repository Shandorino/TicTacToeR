package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.CancelFindUseCase
import javax.inject.Inject

class CancelFindUseCaseImpl @Inject constructor(
    private val socketService: SocketService
): CancelFindUseCase {

    override suspend fun invoke() = socketService.sendPing("cancel-find")

}