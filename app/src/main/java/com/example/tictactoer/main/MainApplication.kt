package com.example.tictactoer.main


import android.app.Application
import com.example.tictactoer.di.useCases.InitConnectionUseCase
import com.example.tictactoer.vk.IAccountService
import com.example.tictactoer.vk.VKAccountService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    //lateinit var appComponent: AppComponent


    lateinit var accountService: IAccountService

    lateinit var retrofit: Retrofit

    companion object {
        lateinit var application: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        accountService = VKAccountService(getSharedPreferences("vk_account", MODE_PRIVATE))
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}