package com.tnc.data.remote.mealdb

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Free-tier endpoints only — permitted by TheMealDB's own terms during development/education.
 * Publishing this app needs their paid supporter key first (tracked separately by the user).
 */
interface MealDbApi {

    @GET("search.php")
    suspend fun searchByFirstLetter(
        @Query("f") letter: String
    ): MealsResponseDto

}
