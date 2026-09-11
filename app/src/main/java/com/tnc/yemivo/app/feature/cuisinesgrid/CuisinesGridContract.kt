package com.tnc.yemivo.app.feature.cuisinesgrid

import com.tnc.yemivo.app.feature.common.CuisineOption

data class CuisinesGridUiState(

    val cuisines: List<CuisineOption> = emptyList()

)

sealed interface CuisinesGridUiEvent {

    data class CuisineClicked(
        val cuisine: String,
        val cuisineLabel: String
    ) : CuisinesGridUiEvent

}

sealed interface CuisinesGridUiEffect {

    data class NavigateToCuisineRecipes(
        val cuisine: String,
        val cuisineLabel: String
    ) : CuisinesGridUiEffect

}
