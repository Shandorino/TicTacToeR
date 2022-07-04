package com.example.tictactoer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoer.data.local.Leader
import com.example.tictactoer.data.remote.dto.User
import com.example.tictactoer.data.remote.socket.SocketService
import com.example.tictactoer.di.useCases.GetLeadersUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class LeadersViewModel @Inject constructor(
    socketService: SocketService,
    private val getLeadersUseCase: GetLeadersUseCase
): ViewModel() {

    private var ss = socketService.action

    private val _leaderList = MutableStateFlow<List<Leader>>(listOf())
    val leadersList = _leaderList.asStateFlow()

    fun getLeaders(){
        viewModelScope.launch {
            getLeadersUseCase.invoke()
            ss.asStateFlow().collect{ postResp ->
                Log.d("============>", postResp.action)
                if (postResp.action == "getLeaders") {
                    postResp.payload?.let {
                        val userListType: Type = object : TypeToken<List<Leader>?>() {}.type
                        val list: List<Leader> = Gson().fromJson(it.toString(), userListType)
                        _leaderList.emit(list)
                    }
                }
            }
        }

    }

}

