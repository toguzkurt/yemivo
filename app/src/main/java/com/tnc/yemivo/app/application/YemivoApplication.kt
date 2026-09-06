package com.tnc.yemivo.app.application

import android.app.Application
import com.tnc.data.di.databaseModule
import com.tnc.data.di.repositoryModule
import com.tnc.data.di.useCaseModule
import com.tnc.data.local.recipe.RecipeSeeder
import com.tnc.yemivo.app.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YemivoApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val recipeSeeder: RecipeSeeder by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YemivoApplication)
            modules(
                databaseModule,
                repositoryModule,
                useCaseModule,
                appModule
            )
        }

        // One-time: populates the empty recipes table from the bundled JSON assets on first
        // launch. Later launches see a non-empty table and RecipeSeeder.seedIfEmpty() no-ops.
        applicationScope.launch {
            recipeSeeder.seedIfEmpty()
        }
    }
}
