package com.tnc.yemivo.app.theme

import android.content.Context

enum class UnitSystem {
    METRIC,
    IMPERIAL
}

/**
 * Persists the chosen unit system only — see the "Ölçü birimi" row wiring in SettingsFragment.
 * Recipe ingredient amounts are free-text from TheMealDB ("1 cup", "500g", "a pinch", ...), not
 * structured quantity+unit pairs, so there is no safe, general way to actually convert displayed
 * amounts without risking a wrong parse turning into a wrong ingredient quantity. This choice is
 * saved for whenever a safe conversion path exists, but doesn't change any displayed text yet.
 */
class UnitPreferences(
    context: Context
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var system: UnitSystem
        get() = when (prefs.getString(KEY_SYSTEM, null)) {
            "imperial" -> UnitSystem.IMPERIAL
            else -> UnitSystem.METRIC
        }
        set(value) {
            prefs.edit().putString(KEY_SYSTEM, value.name.lowercase()).apply()
        }

    private companion object {
        const val PREFS_NAME = "units"
        const val KEY_SYSTEM = "system"
    }

}
