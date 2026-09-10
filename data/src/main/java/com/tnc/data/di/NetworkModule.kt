package com.tnc.data.di

import com.tnc.data.remote.mealdb.MealDbApi
import com.tnc.data.remote.mealdb.RecipeRemoteSeeder
import com.tnc.data.translation.RecipeTranslator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val MEALDB_BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(MEALDB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(MealDbApi::class.java) }

    single { RecipeRemoteSeeder(api = get(), recipeDao = get()) }

    single { RecipeTranslator(recipeDao = get()) }

}
