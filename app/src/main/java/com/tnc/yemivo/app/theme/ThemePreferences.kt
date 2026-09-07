package com.tnc.yemivo.app.theme

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

/**
 * Persists the chosen theme mode and applies it via AppCompatDelegate, which handles restarting
 * any active Activity itself — callers never need to trigger a recreate manually.
 */
class ThemePreferences(
    context: Context
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var mode: ThemeMode
        get() = when (prefs.getString(KEY_MODE, null)) {
            "light" -> ThemeMode.LIGHT
            "dark" -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
        set(value) {
            prefs.edit().putString(KEY_MODE, value.name.lowercase()).apply()
            apply(value)
        }

    fun applySavedMode() {
        apply(mode)
    }

    private fun apply(
        mode: ThemeMode
    ) {
        AppCompatDelegate.setDefaultNightMode(
            when (mode) {
                ThemeMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }

    private companion object {
        const val PREFS_NAME = "theme"
        const val KEY_MODE = "mode"
    }

}
