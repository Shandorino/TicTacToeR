package com.example.tictactoer.vk

import android.content.SharedPreferences

internal class VKAccountService(
    private val sharedPreference: SharedPreferences
) : IAccountService {
    private val TOKEN = "token"
    private val USER_ID = "userId"

    companion object {
        const val SCOPE = "friends,stats"
    }

    override var token: String?
        get() {
            return sharedPreference.getString(TOKEN, null)
        }
        set(value) {
            with(sharedPreference.edit()) {
                if (value == null) {
                    remove(TOKEN)
                }
                else {
                    putString(TOKEN, value)
                }
                apply()
            }
        }

    override var userId: String?
        get() {
            return sharedPreference.getString(USER_ID, null)
        }
        set(value) {
            with(sharedPreference.edit()) {
                if (value == null) {
                    remove(USER_ID)
                }
                else {
                    putString(USER_ID, value)
                }
                apply()
            }
        }
}