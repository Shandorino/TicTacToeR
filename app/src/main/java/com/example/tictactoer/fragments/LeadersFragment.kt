package com.example.tictactoer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.tictactoer.R
import com.example.tictactoer.adapters.LeadersAdapter
import com.example.tictactoer.databinding.FragmentLeadersBinding
import com.example.tictactoer.viewModels.LeadersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LeadersFragment : Fragment(R.layout.fragment_leaders) {

    private val binding: FragmentLeadersBinding by viewBinding()

    private val viewModel: LeadersViewModel by viewModels()

    private val leadersAdapter: LeadersAdapter by lazy { LeadersAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            with(viewModel) {
                getLeaders()
                leadersList.collect {
                    leadersAdapter.setList(it)
                }
            }
        }

        with(binding) {
            leadersRecycler.adapter = leadersAdapter
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() =
            LeadersFragment()
    }
}