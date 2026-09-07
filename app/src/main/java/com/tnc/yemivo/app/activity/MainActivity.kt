package com.tnc.yemivo.app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tnc.yemivo.R
import com.tnc.yemivo.app.theme.LocaleHelper

/**
 * Single-activity shell: every screen is a Fragment destination inside the root NavHost
 * (activity_main.xml, nav_graph.xml) or the nested tabs NavHost (MainTabsFragment).
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Must run from onCreate, not attachBaseContext — calling this before the Activity is
        // fully attached silently fails to persist the locale via LocaleManager on this app's
        // target API levels, even though the call itself throws nothing.
        LocaleHelper.ensureDefaultLocale(this)
    }

}
