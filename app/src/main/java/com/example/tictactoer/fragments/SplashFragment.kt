package com.example.tictactoer.fragments

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.tictactoer.R
import com.example.tictactoer.databinding.FragmentSplashBinding
import com.example.tictactoer.main.MainApplication
import kotlinx.coroutines.delay

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding: FragmentSplashBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            if (isDarkTheme(requireActivity()))
                splashFragmentImage.setImageResource(R.drawable.ic_tic_tac_toe_darktheme_com)
            else
                splashFragmentImage.setImageResource(R.drawable.ic_tic_tac_toe_lighttheme_com)

        }

        lifecycleScope.launchWhenStarted {
            delay(2000)
            Log.d("======>", MainApplication.application.accountService.userId.toString())
            if (MainApplication.application.accountService.userId == null) {
                SplashFragmentDirections.navToLoginFragment().apply {
                    findNavController().navigate(this)

                }
            } else {
                SplashFragmentDirections.navToMainMenuFragment().apply {
                    findNavController().navigate(this)

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
        fun newInstance() = SplashFragment()
    }
}