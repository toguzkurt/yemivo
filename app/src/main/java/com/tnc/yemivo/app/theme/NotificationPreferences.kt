package com.tnc.yemivo.app.theme

import android.content.Context

/**
 * "Günün tarifi bildirimi" genuinely gates whether YemivoApplication generates the daily-recipe
 * entry in the in-app notification feed (see GenerateDailyRecipeNotificationUseCase) — this
 * isn't just a cosmetic switch. "Kampanya ve fırsatlar" only persists a preference: there's no
 * promotional content system anywhere in the app for it to gate yet.
 */
class NotificationPreferences(
    context: Context
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isDailyRecipeEnabled: Boolean
        get() = prefs.getBoolean(KEY_DAILY_RECIPE, true)
        set(value) {
            prefs.edit().putBoolean(KEY_DAILY_RECIPE, value).apply()
        }

    var isCampaignsEnabled: Boolean
        get() = prefs.getBoolean(KEY_CAMPAIGNS, false)
        set(value) {
            prefs.edit().putBoolean(KEY_CAMPAIGNS, value).apply()
        }

    private companion object {
        const val PREFS_NAME = "notification_settings"
        const val KEY_DAILY_RECIPE = "daily_recipe"
        const val KEY_CAMPAIGNS = "campaigns"
    }

}
