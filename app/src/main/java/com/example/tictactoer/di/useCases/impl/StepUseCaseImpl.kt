package com.example.tictactoer.di.useCases.impl

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.StepUseCase
import javax.inject.Inject

class StepUseCaseImpl @Inject constructor(
    private val socketService: SocketService
) : StepUseCase {

    override suspend fun invoke(x: Int, y: Int) = socketService.step(x, y)

}