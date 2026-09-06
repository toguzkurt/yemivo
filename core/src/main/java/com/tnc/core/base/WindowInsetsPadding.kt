package com.tnc.core.base

import android.graphics.Rect
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * Screens draw edge-to-edge by default (targetSdk 35+), so every screen's root view is padded
 * here for the status bar / display cutout / navigation bar instead of repeating this in each
 * layout. Shared by [BaseFragment] and [BaseDialogFragment].
 */
internal fun View.applySystemBarInsetsPadding() {

    val initialPadding = Rect(
        paddingLeft,
        paddingTop,
        paddingRight,
        paddingBottom
    )

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->

        val barsType = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        val bars = insets.getInsets(barsType)

        view.updatePadding(
            left = initialPadding.left + bars.left,
            top = initialPadding.top + bars.top,
            right = initialPadding.right + bars.right,
            bottom = initialPadding.bottom + bars.bottom
        )

        WindowInsetsCompat.Builder(insets)
            .setInsets(barsType, Insets.NONE)
            .build()
    }
}
