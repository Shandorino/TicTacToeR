package com.example.tictactoer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoer.data.remote.dto.SessionInfoDto
import com.example.tictactoer.data.remote.dto.User
import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.CancelFindUseCase
import com.example.tictactoer.di.useCases.FindPlayerUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPlayerViewModel @Inject constructor(
    socketService: SocketService,
    private val findPlayerUseCase: FindPlayerUseCase,
    private val cancelFindUseCase: CancelFindUseCase
): ViewModel() {

    private var ss = socketService.action

    private val _status = MutableStateFlow<SessionInfoDto?>(null)
    val status = _status.asStateFlow()


    fun findGame() {

        viewModelScope.launch {
            findPlayerUseCase.invoke()
            ss.asStateFlow().collect{ postResp ->
                Log.d("=======!!!!!!=====>", postResp.action)
                if (postResp.action == "find-player") {
                    postResp.payload?.let {
                        val session = Gson().fromJson(it.toString(), SessionInfoDto::class.java)
                        _status.emit(session)
                    }
                }
            }
        }

    }

    fun cancelFind() {
        viewModelScope.launch {
            cancelFindUseCase.invoke()
        }
    }

}