package com.example.tictactoer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoer.data.remote.dto.User
import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.GetUserUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    socketService: SocketService,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    private var ss = socketService.action

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()


    fun getUser() {
        viewModelScope.launch {
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