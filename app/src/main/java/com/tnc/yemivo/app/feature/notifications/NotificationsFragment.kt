package com.tnc.yemivo.app.feature.notifications

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentNotificationsBinding
import com.tnc.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(
    FragmentNotificationsBinding::inflate
) {

    private val viewModel: NotificationsViewModel by viewModel()

    private val adapter = NotificationAdapter(
        onItemClick = { notification ->
            viewModel.onEvent(NotificationsUiEvent.NotificationClicked(notification))
        }
    )

    override fun setupViews() = with(binding) {

        rvNotifications.adapter = adapter

    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                render(state)

            }

        }

    }

    override fun observeEffect() {

        launchAndRepeatWithViewLifecycle {

            viewModel.effect.collect { effect ->
                when (effect) {
                    is NotificationsUiEffect.NavigateToRecipeDetail -> {
                        findNavController().navigate(
                            R.id.action_notifications_to_recipeDetail,
                            Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                        )
                    }
                }
            }

        }

    }

    private fun render(
        state: NotificationsUiState
    ) = with(binding) {

        val hasNotifications = state.notifications.isNotEmpty()

        adapter.submitList(state.notifications)

        rvNotifications.isVisible = hasNotifications
        emptyState.isVisible = !hasNotifications

    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
