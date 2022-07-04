package com.example.tictactoer.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.tictactoer.R
import com.example.tictactoer.databinding.FragmentCustomDialogBinding


class CustomDialogFragment : DialogFragment(R.layout.fragment_custom_dialog) {


    private val binding: FragmentCustomDialogBinding by viewBinding()

    private val backToMenu = View.OnClickListener {
        CustomDialogFragmentDirections.navToMainMenuFragment().apply {
            findNavController().navigate(this)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CustomDialogFragmentArgs by navArgs()

        with(binding) {
            when(args.message) {
                "win" -> {
                    text.text = "Поздравляем!!!\nВы победили"
                    button.setOnClickListener(backToMenu)
                }
                "loss" -> {
                    text.text = "Вы проиграли :("
                    button.setOnClickListener(backToMenu)
                }
                "draw" -> {
                    text.text = "Победила дружба))))"
                    button.setOnClickListener(backToMenu)
                }
                "exit-opponent" -> {
                    text.text = "Соединение прервалось или противник вышел из игры"
                    button.setOnClickListener(backToMenu)
                }
                else -> {
                    text.text = args.message
                    button.visibility = View.GONE
                    this@CustomDialogFragment.onDestroy()
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CustomDialogFragment()
    }
}