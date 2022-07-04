package com.example.tictactoer.di.useCases

interface StepUseCase {

    suspend operator fun invoke(x: Int, y: Int)

}