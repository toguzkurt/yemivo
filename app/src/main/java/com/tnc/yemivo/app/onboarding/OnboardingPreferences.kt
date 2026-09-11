package com.tnc.yemivo.app.onboarding

import android.content.Context

/**
 * Remembers whether the 3-page onboarding intro has ever been completed or skipped, so
 * SplashViewModel only routes there on a genuine first launch.
 */
class OnboardingPreferences(
    context: Context
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var hasSeenOnboarding: Boolean
        get() = prefs.getBoolean(KEY_HAS_SEEN, false)
        set(value) {
            prefs.edit().putBoolean(KEY_HAS_SEEN, value).apply()
        }

    private companion object {
        const val PREFS_NAME = "onboarding"
        const val KEY_HAS_SEEN = "has_seen"
    }

}
