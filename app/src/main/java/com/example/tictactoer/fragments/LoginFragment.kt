package com.example.tictactoer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.tictactoer.R
import com.example.tictactoer.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {


    private val binding: FragmentLoginBinding by viewBinding()


    private val navToVKAuth = View.OnClickListener {
        LoginFragmentDirections.navToVKAuthFragment().apply {
            findNavController().navigate(this)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loginButton.setOnClickListener(navToVKAuth)
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}