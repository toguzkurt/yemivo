package com.tnc.yemivo.app.application

import android.app.Application
import com.tnc.data.di.databaseModule
import com.tnc.data.di.networkModule
import com.tnc.data.di.repositoryModule
import com.tnc.data.di.useCaseModule
import com.tnc.yemivo.app.di.appModule
import com.tnc.yemivo.app.theme.ThemePreferences
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YemivoApplication : Application() {

    private val themePreferences: ThemePreferences by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YemivoApplication)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                appModule
            )
        }

        // Must happen before any Activity is created so the very first screen already renders
        // in the saved mode rather than flashing the default then switching.
        themePreferences.applySavedMode()

        // Recipe seeding, translation, and the daily-notification check now run from
        // SplashViewModel (feature/splash) so the user sees real progress instead of them
        // happening silently in the background.
    }
}
