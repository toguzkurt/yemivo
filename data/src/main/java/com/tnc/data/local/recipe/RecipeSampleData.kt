package com.tnc.data.local.recipe

import com.tnc.domain.recipe.model.Recipe

/**
 * Seeds the in-memory [com.tnc.data.repository.RecipeRepositoryImpl] until a real backend/Room
 * data source replaces it. Content mirrors the sample recipes shown across the Yemivo mockups
 * (home, search, favorites, surprise recipe).
 */
internal fun sampleRecipes(): List<Recipe> = listOf(
    Recipe(
        id = "manti",
        name = "Mantı",
        cuisine = "turkish",
        cuisineLabel = "Türk mutfağı",
        durationMinutes = 45,
        servings = 4,
        isFavorite = true,
        steps = listOf(
            "Hamuru yoğurup 20 dakika dinlendirin.",
            "İç harcı için kıymayı soğan ve baharatlarla karıştırın.",
            "Hamuru ince açıp küçük kareler halinde kesin."
        )
    ),
    Recipe(
        id = "margherita-pizza",
        name = "Margherita Pizza",
        cuisine = "italian",
        cuisineLabel = "İtalyan mutfağı",
        durationMinutes = 30,
        servings = 4,
        isFavorite = true,
        steps = listOf(
            "Pizza hamurunu yayıp domates sosu ile kaplayın.",
            "Mozzarella peyniri ve fesleğen ile üstünü kapatın.",
            "220°C fırında kenarları altın rengi olana kadar pişirin."
        )
    ),
    Recipe(
        id = "tavuk-sis",
        name = "Tavuk Şiş",
        cuisine = "turkish",
        cuisineLabel = "Türk mutfağı",
        durationMinutes = 25,
        servings = 4,
        isFavorite = false,
        steps = listOf(
            "Tavuk göğsünü küp küp doğrayıp baharatlarla marine edin.",
            "Şişlere dizip mangalda ya da fırında çevirerek pişirin."
        )
    ),
    Recipe(
        id = "tavuk-sote",
        name = "Tavuk Sote",
        cuisine = "turkish",
        cuisineLabel = "Türk mutfağı",
        durationMinutes = 20,
        servings = 4,
        isFavorite = false,
        steps = listOf(
            "Tavuk ve sebzeleri küçük parçalar halinde doğrayın.",
            "Yüksek ateşte kısa sürede soteleyip baharatlayın."
        )
    ),
    Recipe(
        id = "sezar-salata",
        name = "Sezar Salata",
        cuisine = "american",
        cuisineLabel = "Amerikan mutfağı",
        durationMinutes = 15,
        servings = 2,
        isFavorite = true,
        steps = listOf(
            "Marul yapraklarını yıkayıp doğrayın.",
            "Sezar sos, parmesan ve kruton ile karıştırın."
        )
    ),
    Recipe(
        id = "karniyarik",
        name = "Karnıyarık",
        cuisine = "turkish",
        cuisineLabel = "Türk mutfağı",
        durationMinutes = 50,
        servings = 4,
        isFavorite = false,
        steps = listOf(
            "Patlıcanları kızartıp ortalarını yarın.",
            "Kıymalı harcı soğan ve domates ile hazırlayın.",
            "Patlıcanların içini doldurup fırında pişirin."
        )
    )
)
