package com.tnc.data.local.recipe

import com.google.gson.annotations.SerializedName

/**
 * Matches the field names used by both the bundled recipe JSON assets: the real Turkish recipe
 * dataset (104bit/turkish-recipe-dataset, MIT licensed) and the originally-authored Italian/
 * Mexican/Japanese recipes written in the same shape for a single shared parser.
 */
data class RecipeAssetDto(

    @SerializedName("tarif_adi")
    val name: String,

    @SerializedName("kategori")
    val category: String,

    @SerializedName("porsiyon")
    val servings: Double?,

    @SerializedName("hazirlik_suresi_dk")
    val prepMinutes: Double?,

    @SerializedName("pisirme_suresi_dk")
    val cookMinutes: Double?,

    @SerializedName("zorluk")
    val difficulty: String?,

    @SerializedName("malzemeler")
    val ingredients: List<IngredientAssetDto>,

    @SerializedName("yapilis_adimlari")
    val steps: List<String>
)

data class IngredientAssetDto(

    @SerializedName("isim")
    val name: String,

    @SerializedName("miktar")
    val amount: String?,

    @SerializedName("birim")
    val unit: String?
)
