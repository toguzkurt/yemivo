package com.tnc.data.di

import androidx.room.Room
import com.tnc.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "yemivo.db"
        )
            // No production installs yet — safe to wipe on schema change rather than migrate.
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single { get<AppDatabase>().recipeDao() }

    single { get<AppDatabase>().shoppingListDao() }

    single { get<AppDatabase>().notificationDao() }

}
