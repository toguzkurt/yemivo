package com.tnc.yemivo.app.session

import android.content.Context

/**
 * There's no real auth backend anywhere in the app (see LoginFragment) — this just remembers
 * that the local "login" form was submitted, so Profile can show something other than a
 * permanent "Misafir kullanıcı" and offer a real "Çıkış yap".
 */
class SessionPreferences(
    context: Context
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    val isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_LOGGED_IN, false)

    val userEmail: String?
        get() = prefs.getString(KEY_EMAIL, null)

    fun login(
        email: String
    ) {
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    private companion object {
        const val PREFS_NAME = "session"
        const val KEY_LOGGED_IN = "logged_in"
        const val KEY_EMAIL = "email"
    }

}
