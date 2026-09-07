package com.tnc.data.local.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list_items ORDER BY recipeName ASC, ingredientName ASC")
    fun getAll(): Flow<List<ShoppingListItemEntity>>

    // IGNORE (not REPLACE): re-adding a recipe already on the list must not reset ingredients
    // the user already checked off back to unchecked.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<ShoppingListItemEntity>)

    @Query("SELECT isChecked FROM shopping_list_items WHERE id = :id")
    suspend fun isChecked(id: String): Boolean?

    @Query("UPDATE shopping_list_items SET isChecked = :isChecked WHERE id = :id")
    suspend fun setChecked(id: String, isChecked: Boolean)

    @Query("DELETE FROM shopping_list_items")
    suspend fun clearAll()

}
