package com.tnc.yemivo.app.feature.cookmode

import com.tnc.domain.recipe.model.Recipe

data class CookModeUiState(

    val recipe: Recipe? = null,

    val currentStepIndex: Int = 0,

    val totalSteps: Int = 0

)

sealed interface CookModeUiEvent {

    data object NextClicked : CookModeUiEvent

    data object PrevClicked : CookModeUiEvent

}

sealed interface CookModeUiEffect
