package com.tnc.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Generic RecyclerView adapter over any item type. A concrete adapter only describes how to
 * inflate a row's binding and how to bind one item to it — DiffUtil-based diffing (via
 * [ListAdapter]) and ViewHolder wiring are handled here so nothing repeats that per screen.
 */
abstract class BaseAdapter<T : Any, VB : ViewBinding>(
    private val bindingInflater: (
        LayoutInflater,
        ViewGroup?,
        Boolean
    ) -> VB,
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClick: ((T) -> Unit)? = null
) : ListAdapter<T, BaseAdapter.BaseViewHolder<VB>>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<VB> {

        val binding = bindingInflater(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = BaseViewHolder(binding)

        onItemClick?.let { listener ->
            binding.root.setOnClickListener {
                val position = holder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener(getItem(position))
                }
            }
        }

        return holder
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<VB>,
        position: Int
    ) {
        onBind(holder.binding, getItem(position), position)
    }

    /**
     * Bind [item] into [binding]. [position] is the adapter position, not the item's own id.
     */
    protected abstract fun onBind(
        binding: VB,
        item: T,
        position: Int
    )

    class BaseViewHolder<VB : ViewBinding>(
        val binding: VB
    ) : RecyclerView.ViewHolder(binding.root)
}
