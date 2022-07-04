package com.example.tictactoer.di

import com.example.tictactoer.di.useCases.*
import com.example.tictactoer.di.useCases.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsInitConnectionUseCase(initConnectionUseCaseImpl: InitConnectionUseCaseImpl): InitConnectionUseCase


    @Binds
    fun bindsSendPingUseCase(pingUseCaseImpl: PingUseCaseImpl): PingUseCase


    @Binds
    fun bindsGetUserUseCase(getUserUseCaseImpl: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    fun bindsGetLeadersUseCase(getLeadersUseCaseImpl: GetLeadersUseCaseImpl): GetLeadersUseCase

    @Binds
    fun bindsFindPlayerUseCase(findPlayerUseCaseImpl: FindPlayerUseCaseImpl): FindPlayerUseCase

    @Binds
    fun bindsCancelFindUseCase(cancelFindUseCaseImpl: CancelFindUseCaseImpl): CancelFindUseCase

    @Binds
    fun bindsStepUseCase(stepUseCaseImpl: StepUseCaseImpl): StepUseCase

}