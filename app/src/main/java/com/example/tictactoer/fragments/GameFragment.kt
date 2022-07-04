package com.example.tictactoer.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.tictactoer.R
import com.example.tictactoer.data.remote.dto.User
import com.example.tictactoer.databinding.FragmentGameBinding
import com.example.tictactoer.main.MainApplication
import com.example.tictactoer.viewModels.GameFragmentViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val binding: FragmentGameBinding by viewBinding()

    private val viewModel: GameFragmentViewModel by viewModels()

    private var fieldImg: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

    private val index0 = MutableStateFlow(fieldImg[0])
    private val index1 = MutableStateFlow(fieldImg[1])
    private val index2 = MutableStateFlow(fieldImg[2])
    private val index3 = MutableStateFlow(fieldImg[3])
    private val index4 = MutableStateFlow(fieldImg[4])
    private val index5 = MutableStateFlow(fieldImg[5])
    private val index6 = MutableStateFlow(fieldImg[6])
    private val index7 = MutableStateFlow(fieldImg[7])
    private val index8 = MutableStateFlow(fieldImg[8])

    private var indexList = listOf(index0, index1, index2, index3, index4, index5, index6, index7, index8)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: GameFragmentArgs by navArgs()

        viewModel.getFieldInfo()

        with(binding) {
            when(MainApplication.application.accountService.userId) {
                args.userId -> {
                    userName.text = args.userName
                    Glide.with(binding.root).load(args.userUrl).centerCrop().into(userImage)
                    opponentName.text = args.opponentName
                    Glide.with(binding.root).load(args.opponentUrl).centerCrop().into(opponentImage)
                }
                else -> {
                    opponentName.text = args.userName
                    Glide.with(binding.root).load(args.userUrl).centerCrop().into(opponentImage)
                    userName.text = args.opponentName
                    Glide.with(binding.root).load(args.opponentUrl).centerCrop().into(userImage)
                }
            }

            when(args.nowPlayerId) {
                MainApplication.application.accountService.userId -> {
                    stepRight.text = "Сейчас ваш ход"
                }
                else -> {
                    stepRight.text = "Ход противника"
                }
            }

            b00.setOnClickListener {
                viewModel.userStep(0, 0)
            }
            b01.setOnClickListener {
                viewModel.userStep(1, 0)
            }
            b02.setOnClickListener {
                viewModel.userStep(2, 0)
            }
            b10.setOnClickListener {
                viewModel.userStep(0, 1)
            }
            b11.setOnClickListener {
                viewModel.userStep(1, 1)
            }
            b12.setOnClickListener {
                viewModel.userStep(2, 1)
            }
            b20.setOnClickListener {
                viewModel.userStep(0, 2)
            }
            b21.setOnClickListener {
                viewModel.userStep(1, 2)
            }
            b22.setOnClickListener {
                viewModel.userStep(2, 2)
            }



        }

        lifecycleScope.launchWhenCreated {
            viewModel.gameState.collect { state ->
                when (state) {
                    "loss" -> {
                        GameFragmentDirections.navToCustomDialogFragment("loss").apply {
                            findNavController().navigate(this)
                        }
                    }
                    "win" -> {
                        GameFragmentDirections.navToCustomDialogFragment("win").apply {
                            findNavController().navigate(this)
                        }
                    }
                    "draw" -> {
                        GameFragmentDirections.navToCustomDialogFragment("draw").apply {
                            findNavController().navigate(this)
                        }
                    }
                    "exit-opponent" -> {
                        GameFragmentDirections.navToCustomDialogFragment("exit-opponent").apply {
                            findNavController().navigate(this)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.gameField.collect { session ->
                session?.let {
                    if (session.message == null) {
                        var charArray =
                            session._gameField.removeSurrounding("[", "]").split(",", "[", "]")
                                .map { it.toString() }

                        val nowPlayer = Gson().fromJson(session.nowPlayer, User::class.java)

                        if (nowPlayer != null) {
                            with(binding) {
                                when (nowPlayer.id.toString()) {
                                    MainApplication.application.accountService.userId -> {
                                        stepRight.text = "Сейчас ваш ход"
                                    }
                                    else -> {
                                        stepRight.text = "Ход противника"
                                    }
                                }



                            }
                        }

                        var z = 0
                        for (i in charArray) {
                            if (i.isNotEmpty()) {
                                fieldImg[z] = i.toInt()
                                z++
                            }
                        }

                        reload(fieldImg)


                    }else {
                        GameFragmentDirections.navToCustomDialogFragment(session.message!!).apply {
                            findNavController().navigate(this)
                        }
                    }

                }

            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index0.collect {
                    b00.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index1.collect {
                    b01.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index2.collect {
                    b02.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index3.collect {
                    b10.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index4.collect {
                    b11.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index5.collect {
                    b12.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index6.collect {
                    b20.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index7.collect {
                    b21.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            with(binding) {
                index8.collect {
                    b22.setImageResource(
                        when (it) {
                            1 -> {
                                R.drawable.ic_cross_svgrepo_com
                            }
                            2 -> {
                                R.drawable.ic_circle_svgrepo_com
                            }
                            else -> {
                                R.drawable.ic_empty
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()


    }

    val scope = CoroutineScope(Dispatchers.Default)
    fun reload(rel: MutableList<Int>) {
        var str = "В релоаде: "
        scope.launch {
            for (i in 0..8){
                indexList[i].emit(rel[i])
                str += rel[i]
            }
            Log.i("qwe", str)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            GameFragment()
    }
}