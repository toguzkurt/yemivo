package com.tnc.core.extensions

import android.content.Context
import android.widget.Toast
import com.tnc.core.common.result.UiText

fun Context.showToast(
    message: UiText
) {
    Toast.makeText(
        this,
        message.asString(this),
        Toast.LENGTH_LONG
    ).show()
}
