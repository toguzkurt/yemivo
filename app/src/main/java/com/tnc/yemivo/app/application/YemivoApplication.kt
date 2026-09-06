package com.tnc.yemivo.app.application

import android.app.Application
import com.tnc.yemivo.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YemivoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YemivoApplication)
            modules(appModule)
        }
    }
}
