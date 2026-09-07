package com.tnc.yemivo.app.feature.common

import android.content.Context
import com.tnc.domain.notification.model.AppNotification
import com.tnc.domain.notification.model.NotificationType
import com.tnc.yemivo.R
import java.util.concurrent.TimeUnit

fun AppNotification.title(
    context: Context
): String = context.getString(
    when (type) {
        NotificationType.DOWNLOAD_COMPLETE -> R.string.notif_download_title
        NotificationType.DAILY_RECIPE -> R.string.notif_daily_recipe_title
    }
)

fun AppNotification.message(
    context: Context
): String = context.getString(
    when (type) {
        NotificationType.DOWNLOAD_COMPLETE -> R.string.notif_download_message
        NotificationType.DAILY_RECIPE -> R.string.notif_daily_recipe_message
    },
    recipeName.orEmpty()
)

/**
 * Real elapsed time (e.g. "2 saat önce"), computed and formatted ourselves rather than via
 * DateUtils.getRelativeTimeSpanString — that resolves its own strings against the device's
 * system locale, which renders in English on an English-locale device even though this app has
 * no English resources at all and shows Turkish everywhere else regardless of device locale.
 */
fun AppNotification.relativeTime(
    context: Context
): String {

    val elapsedMillis = (System.currentTimeMillis() - timestamp).coerceAtLeast(0)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis)
    val days = TimeUnit.MILLISECONDS.toDays(elapsedMillis)

    return when {
        minutes < 1 -> context.getString(R.string.notif_time_just_now)
        hours < 1 -> context.getString(R.string.notif_time_minutes_ago, minutes.toInt())
        days < 1 -> context.getString(R.string.notif_time_hours_ago, hours.toInt())
        else -> context.getString(R.string.notif_time_days_ago, days.toInt())
    }

}
