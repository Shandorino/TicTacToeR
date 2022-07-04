package com.example.tictactoer.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoer.data.local.Leader
import com.example.tictactoer.data.remote.dto.User
import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.GetUserUseCase
import com.example.tictactoer.di.useCases.InitConnectionUseCase
import com.example.tictactoer.di.useCases.PingUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class MainMenuViewModel @Inject constructor(
    socketService: SocketService,
    initConnectionUseCase: InitConnectionUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    private var ss = socketService.action

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            initConnectionUseCase.invoke()
        }
    }

    fun getUser() {

        viewModelScope.launch {
            delay(500)
            getUserUseCase.invoke()
            ss.asStateFlow().collect{ postResp ->
                Log.d("=======!!!!!!=====>", postResp.action)
                if (postResp.action == "getUser") {
                    postResp.payload?.let {
                        val user: User = Gson().fromJson(it.toString(), User::class.java)
                        _user.emit(user)
                    }
                }
            }
        }
    }

}