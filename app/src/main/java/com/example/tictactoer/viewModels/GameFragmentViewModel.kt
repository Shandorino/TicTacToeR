package com.example.tictactoer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoer.data.remote.dto.GameFieldDto
import com.example.tictactoer.data.remote.dto.SessionInfoDto
import com.example.tictactoer.data.remote.dto.SessionStateDto
import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.StepUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    socketService: SocketService,
    private val stepUseCase: StepUseCase,
): ViewModel() {


    private val ss = socketService.action

    private val _gameField = MutableStateFlow<GameFieldDto?>(null)
    val gameField = _gameField.asStateFlow()

    private val _gameState = MutableStateFlow<String>("")
    val gameState = _gameState.asStateFlow()


    fun getFieldInfo() {
        viewModelScope.launch {
            ss.asStateFlow().collect{ postResp ->
                Log.d("=======!!!!!!=====>", postResp.action)
                if (postResp.action == "step") {
                    postResp.payload?.let {
                        val session = Gson().fromJson(it.toString(), GameFieldDto::class.java)
                        _gameField.emit(session)
                    }
                }
            }
        }

        viewModelScope.launch {
            ss.asStateFlow().collect{ postResp ->
                Log.d("=======!!!!!!=====>", postResp.action)
                if (postResp.action == "gameState") {
                    postResp.payload?.let {
                        val session = Gson().fromJson(it.toString(), SessionStateDto::class.java)
                        val gameFiel = Gson().fromJson(session.session, GameFieldDto::class.java)
                        _gameField.emit(gameFiel)
                        _gameState.emit(session.state)
                    }
                }
            }
        }

        viewModelScope.launch {
            ss.asStateFlow().collect{ postResp ->
                Log.d("=======!!!!!!=====>", postResp.action)
                if (postResp.action == "exit-opponent") {
                    postResp.payload?.let {
                        _gameState.emit("exit-opponent")
                    }
                }
            }
        }
    }


    fun userStep(x: Int, y: Int) {
        viewModelScope.launch {
            stepUseCase.invoke(x, y)
        }
    }


}