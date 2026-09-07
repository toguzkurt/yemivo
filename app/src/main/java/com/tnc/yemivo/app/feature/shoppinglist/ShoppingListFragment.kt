package com.tnc.yemivo.app.feature.shoppinglist

import android.content.Intent
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentShoppingListBinding
import com.tnc.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingListFragment : BaseFragment<FragmentShoppingListBinding>(
    FragmentShoppingListBinding::inflate
) {

    private val viewModel: ShoppingListViewModel by viewModel()

    private val adapter = ShoppingListAdapter(
        onItemClick = { item ->
            viewModel.onEvent(ShoppingListUiEvent.ItemToggled(item.id))
        }
    )

    override fun setupViews() = with(binding) {

        rvItems.adapter = adapter

    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        ivDelete.setOnClickListener {
            confirmClearAll()
        }

        btnShare.setOnClickListener {
            viewModel.onEvent(ShoppingListUiEvent.ShareClicked)
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
                    is ShoppingListUiEffect.ShareList -> {
                        shareList(effect.text)
                    }
                }
            }

        }

    }

    private fun render(
        state: ShoppingListUiState
    ) = with(binding) {

        val hasItems = state.items.isNotEmpty()

        adapter.submitList(state.items)

        rvItems.isVisible = hasItems
        btnShare.isVisible = hasItems
        ivDelete.isVisible = hasItems
        tvEmpty.isVisible = !hasItems

    }

    private fun confirmClearAll() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.shopping_list_clear_confirm_title)
            .setMessage(R.string.shopping_list_clear_confirm_message)
            .setPositiveButton(R.string.action_clear) { _, _ ->
                viewModel.onEvent(ShoppingListUiEvent.ClearAllClicked)
            }
            .setNegativeButton(R.string.action_cancel, null)
            .show()
    }

    private fun shareList(
        text: String
    ) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.action_share_list)))
    }

}
