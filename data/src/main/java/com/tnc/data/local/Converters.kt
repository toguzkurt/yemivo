package com.tnc.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tnc.data.local.recipe.RecipeIngredientEntity

/**
 * Recipes have a variable-length ingredient/step list per row — storing them as Gson-serialized
 * JSON columns is far simpler than a junction table for data that's always read/written whole
 * (never queried by individual ingredient).
 */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromIngredientList(value: List<RecipeIngredientEntity>): String = gson.toJson(value)

    @TypeConverter
    fun toIngredientList(value: String): List<RecipeIngredientEntity> =
        gson.fromJson(value, object : TypeToken<List<RecipeIngredientEntity>>() {}.type)

}
