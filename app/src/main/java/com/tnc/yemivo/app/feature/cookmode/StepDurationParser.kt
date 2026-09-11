package com.tnc.yemivo.app.feature.cookmode

/**
 * Looks for a duration mentioned in a recipe step's free text (e.g. "rest for 10 minutes",
 * "1 saat 30 dakika bekletin") and returns it in seconds. Recognizes both Turkish and English
 * keywords since the step text's language depends on the app's current locale. Returns null when
 * no duration is mentioned — most steps don't have one.
 */
fun parseDurationSeconds(stepText: String): Int? {

    val hours = Regex("""(\d+)\s*(saat|hours?|hrs?)""", RegexOption.IGNORE_CASE)
        .find(stepText)?.groupValues?.get(1)?.toIntOrNull() ?: 0

    val minutes = Regex("""(\d+)\s*(dakika|dk|minutes?|mins?)""", RegexOption.IGNORE_CASE)
        .find(stepText)?.groupValues?.get(1)?.toIntOrNull() ?: 0

    val totalSeconds = hours * 3600 + minutes * 60

    return totalSeconds.takeIf { it > 0 }

}
