package com.example.tictactoer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tictactoer.R
import com.example.tictactoer.main.MainApplication
import com.example.tictactoer.vk.AuthStatus
import com.example.tictactoer.vk.AuthWebViewClient
import com.example.tictactoer.vk.VKAccountService
import java.net.URLEncoder
import java.util.regex.Pattern


class VKAuthFragment : Fragment() {

    private val webview by lazy { WebView(requireContext()) }
    private val _authParams = StringBuilder("https://oauth.vk.com/authorize?").apply {
        append(String.format("%s=%s", URLEncoder.encode("client_id", "UTF-8"), URLEncoder.encode("6287487", "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("redirect_uri", "UTF-8"), URLEncoder.encode("https://oauth.vk.com/blank.html", "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("display", "UTF-8"), URLEncoder.encode("mobile", "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("scope", "UTF-8"), URLEncoder.encode(VKAccountService.SCOPE, "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("response_type", "UTF-8"), URLEncoder.encode("token", "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("v", "UTF-8"), URLEncoder.encode("5.131", "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("state", "UTF-8"), URLEncoder.encode("12345", "UTF-8")) + "&")
        append(String.format("%s=%s", URLEncoder.encode("revoke", "UTF-8"), URLEncoder.encode("1", "UTF-8")))
    }.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = webview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (MainApplication.application.accountService.token == null) {
            webview.webViewClient = AuthWebViewClient(requireContext()) { status ->
                when(status) {
                    AuthStatus.AUTH -> {

                    }
                    AuthStatus.CONFIRM -> {

                    }
                    AuthStatus.ERROR -> {
                        Toast.makeText(context, "Не верный логин или пароль", Toast.LENGTH_LONG).show()
                    }
                    AuthStatus.BLOCKED -> {
                        showAuthWindow()
                        Toast.makeText(context, "Аккаунт заблокирован", Toast.LENGTH_LONG).show()
                    }
                    AuthStatus.SUCCESS -> {
                        val url = webview.url!!
                        val tokenMather = Pattern.compile("access_token=\\w+").matcher(url)
                        val userIdMather = Pattern.compile("user_id=\\w+").matcher(url)
                        // Если есть совпадение с патерном.
                        if (tokenMather.find() && userIdMather.find()) {
                            val token = tokenMather.group().replace("access_token=".toRegex(), "")
                            val userId = userIdMather.group().replace("user_id=".toRegex(), "")
                            // Если токен и id получен.
                            if (token.isNotEmpty() && userId.isNotEmpty()) {
                                MainApplication.application.accountService.token = token
                                MainApplication.application.accountService.userId = userId
                                navigateToMainMenu()
                            }
                        }
                    }
                }
            }
        } else {
            navigateToMainMenu()
        }
    }

    override fun onStart() {
        super.onStart()
        if (MainApplication.application.accountService.token == null) {
            showAuthWindow()
        }
    }

    private fun showAuthWindow() {
        CookieManager.getInstance().removeAllCookies(null)
        webview.loadUrl(_authParams)
    }

    private fun navigateToMainMenu() {
        VKAuthFragmentDirections.navToMainMenuFragment().apply {
            findNavController().navigate(this)
        }
    }
}