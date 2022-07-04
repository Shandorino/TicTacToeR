package com.example.tictactoer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.tictactoer.R
import com.example.tictactoer.data.remote.dto.User
import com.example.tictactoer.databinding.FragmentFindPlayerBinding
import com.example.tictactoer.viewModels.FindPlayerViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPlayerFragment : DialogFragment(R.layout.fragment_find_player) {


    private val binding: FragmentFindPlayerBinding by viewBinding()

    private val viewModel: FindPlayerViewModel by viewModels()

    private val cancelSearch = View.OnClickListener {
        viewModel.cancelFind()
        findNavController().popBackStack()
    }


    private fun toGameFragment(nowPlayerId: String, userName: String, userUrl: String, opponentName: String, opponentUrl: String, userId: String, opponentId: String) {
        FindPlayerFragmentDirections.navToGameFragment(nowPlayerId = nowPlayerId,
            userName = userName,
            userUrl = userUrl,
            opponentName = opponentName,
            opponentUrl = opponentUrl,
            userId = userId,
            opponentId = opponentId
            ).apply {
            findNavController().navigate(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cancelButton.setOnClickListener(cancelSearch)
        }


        lifecycleScope.launchWhenCreated {
            viewModel.findGame()
            viewModel.status.collect { status ->
                status.let {
                    if (it != null) {
                        if (it.status != "find") {
                            val gson = Gson()
                            val user = gson.fromJson(it.firstPlayer, User::class.java)
                            val opponent = gson.fromJson(it.secondPlayer, User::class.java)
                            val nowPlayer = gson.fromJson(it.nowPlayer, User::class.java)
                            toGameFragment(
                                nowPlayerId = nowPlayer.id.toString(),
                                userUrl = user.photoURL,
                                userName = "${user.firstName}\n${user.lastName}",
                                opponentName = "${opponent.firstName}\n${opponent.lastName}",
                                opponentUrl = opponent.photoURL,
                                userId = user.id.toString(),
                                opponentId = opponent.id.toString()
                            )
                        }
                    }
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FindPlayerFragment()
    }
}