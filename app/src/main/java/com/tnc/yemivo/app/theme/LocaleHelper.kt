package com.tnc.yemivo.app.theme

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/**
 * Calls the platform LocaleManager directly on API 33+ rather than going through
 * AppCompatDelegate.setApplicationLocales() — that call silently no-ops in this app's test
 * environment (confirmed via `adb shell cmd locale get-app-locales` staying empty right after
 * calling it, while the equivalent `cmd locale set-app-locales` shell command works correctly),
 * so it can't be trusted as the only path. AppCompatDelegate stays as the pre-33 fallback, where
 * LocaleManager doesn't exist.
 */
object LocaleHelper {

    const val TAG_TURKISH = "tr"
    const val TAG_ENGLISH = "en"

    private const val PREFS_NAME = "locale"
    private const val KEY_DEFAULT_APPLIED = "default_applied"

    /**
     * Without an explicit per-app locale, Android resolves strings against the device's own
     * locale list — now that values-en/ exists, that would silently flip the app to English on
     * any English-locale device. Pin Turkish as the actual default on first run, same as before
     * values-en existed, while still leaving real switching available from Settings.
     */
    fun ensureDefaultLocale(
        context: Context
    ) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (!prefs.getBoolean(KEY_DEFAULT_APPLIED, false)) {
            setLocale(context, TAG_TURKISH)
            prefs.edit().putBoolean(KEY_DEFAULT_APPLIED, true).apply()
        }
    }

    fun currentTag(
        context: Context
    ): String {
        val language = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)?.applicationLocales
                ?.takeUnless { it.isEmpty }
                ?.get(0)
                ?.language
        } else {
            AppCompatDelegate.getApplicationLocales()
                .takeUnless { it.isEmpty }
                ?.get(0)
                ?.language
        }
        return language ?: TAG_TURKISH
    }

    fun setLocale(
        context: Context,
        tag: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)?.applicationLocales =
                LocaleList.forLanguageTags(tag)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
        }
    }

}
