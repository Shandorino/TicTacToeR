package com.example.tictactoer.di

import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.data.remote.socket.impl.SocketServiceImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideSocketService(client: HttpClient): SocketService {
        return SocketServiceImpl(client)
    }

}