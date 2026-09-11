package com.tnc.yemivo.app.feature.portionadjuster

import com.tnc.domain.recipe.model.Recipe

data class PortionAdjusterUiState(

    val recipe: Recipe? = null,

    val currentServings: Int = 0

)

sealed interface PortionAdjusterUiEvent {

    data object IncrementClicked : PortionAdjusterUiEvent

    data object DecrementClicked : PortionAdjusterUiEvent

}

sealed interface PortionAdjusterUiEffect
