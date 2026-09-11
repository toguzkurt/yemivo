package com.tnc.yemivo.app.feature.splash

data class SplashUiState(

    val progress: Float = 0f

)

sealed interface SplashUiEffect {

    data object NavigateToMain : SplashUiEffect

    data object NavigateToOnboarding : SplashUiEffect

}
