package com.example.tictactoer.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.tictactoer.R
import com.example.tictactoer.databinding.FragmentMainMenuBinding
import com.example.tictactoer.main.MainApplication
import com.example.tictactoer.viewModels.MainMenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {

    private val binding: FragmentMainMenuBinding by viewBinding()

    private val viewModel: MainMenuViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private val toUSerInfo = View.OnClickListener {
        MainMenuFragmentDirections.navToUserInfoFragment().apply {
            findNavController().navigate(this)
        }
    }

    private val toLeaderScreen = View.OnClickListener {
        MainMenuFragmentDirections.navToLeadersFragment().apply {
            findNavController().navigate(this)
        }
    }

    private val toFindPlayer = View.OnClickListener {
        MainMenuFragmentDirections.navToFindPlayerFragment().apply {
            findNavController().navigate(this)
        }
    }


    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            if (isDarkTheme(requireActivity()))
                logo.setImageResource(R.drawable.ic_tic_tac_toe_darktheme_com)
            else
                logo.setImageResource(R.drawable.ic_tic_tac_toe_lighttheme_com)


            findGameButton.setOnClickListener(toFindPlayer)

            leaderList.setOnClickListener(toLeaderScreen)

            lifecycleScope.launchWhenStarted {
                viewModel.getUser()
                viewModel.user.collect {
                    with(binding) {
                        if (it != null) {
                            Glide.with(binding.root).load(it.photoURL).centerCrop().into(userImage)
                            fullName.text = "${it.firstName}\n${it.lastName}"
                            userButton.setOnClickListener(toUSerInfo)
                        }
                    }
                }
            }

        }
    }


    fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainMenuFragment()
    }
}