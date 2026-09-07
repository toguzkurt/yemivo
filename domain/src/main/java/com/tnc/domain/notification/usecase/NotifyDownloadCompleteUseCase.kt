package com.tnc.domain.notification.usecase

import com.tnc.domain.notification.repository.NotificationRepository
import com.tnc.domain.recipe.model.Recipe

class NotifyDownloadCompleteUseCase(
    private val repository: NotificationRepository
) {

    suspend operator fun invoke(
        recipe: Recipe
    ) {
        repository.notifyDownloadComplete(recipe)
    }

}
