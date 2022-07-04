package com.example.tictactoer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.tictactoer.R
import com.example.tictactoer.databinding.FragmentUserInfoBinding
import com.example.tictactoer.viewModels.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : DialogFragment(R.layout.fragment_user_info) {

    private val binding: FragmentUserInfoBinding by viewBinding()

    private val viewModel: UserInfoViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            lifecycleScope.launchWhenStarted {
                viewModel.getUser()
                viewModel.user.collect {
                    with(binding) {
                        if (it != null) {
                            Glide.with(binding.root).load(it.photoURL).centerCrop().into(userImage)
                            userName.text = "${it.firstName}\n${it.lastName}"
                            ratingNumber.text = it.rating.toString()
                        }
                    }
                }
            }


        }

    }



    companion object {

        @JvmStatic
        fun newInstance() =
            UserInfoFragment()
    }
}